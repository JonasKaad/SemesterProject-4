package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleAI implements IBattleAI {

    private KnowledgeState knowledgeState;
    private IBattleParticipant participantToControl;
    private IBattleParticipant opposingParticipant;
    private IBattleSimulation battleSimulation;
    private IMonsterMove emptyMove = new EmptyMove();
    private boolean useAlphaBetaPruning = true;
    long startTime;
    int maxDepthReached = 0;
    int timeLimitms = 1000;


    public BattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        knowledgeState = new KnowledgeState();
        this.participantToControl = participantToControl;
        this.opposingParticipant = participantToControl == battleSimulation.getState().getPlayer()
                ? battleSimulation.getState().getEnemy()
                : battleSimulation.getState().getPlayer();
        this.battleSimulation = battleSimulation;
    }

    public void opposingMonsterUsedMove(IMonster monster, IMonsterMove move) {
        // If we don't know anything about the monsters moves yet, add an empty list
        if (!knowledgeState.getMonsterMoves().containsKey(monster)){
            knowledgeState.getMonsterMoves().put(monster, new ArrayList<>());
        }
        // If this is the first time we see this monster using this move, add the move to
        // the list of known moves for that monster
        if (!knowledgeState.getMonsterMoves().get(monster).contains(move)) {
            knowledgeState.getMonsterMoves().get(monster).add(move);
        }
    }

    public boolean outOfTime() {
        return ((System.nanoTime()-startTime)/1000000)>timeLimitms;
    }

    @Override
    public void doAction(IBattleSimulation b) {

        // Update state, should the enemy have changed their monster
        if (!knowledgeState.getEnemyMonsters().contains(opposingParticipant.getActiveMonster())) {
            knowledgeState.getEnemyMonsters().add(opposingParticipant.getActiveMonster());
        }

        // Try minmax on all available actions here
        IMonsterMove bestMove = null;
        IMonster bestSwitch = null;
        float bestMoveUtil = -Float.MAX_VALUE;
        float bestSwitchUtil = -Float.MAX_VALUE;

        int searchDepth = 1;
        startTime = System.nanoTime();
        maxDepthReached = 0;

        while (!outOfTime()) {

            IMonsterMove newBestMove = bestMove;
            IMonster newBestSwitch = bestSwitch;
            float newBestMoveUtil = bestMoveUtil;
            float newBestSwitchUtil = bestSwitchUtil;

            int oldMaxSearchDepth = maxDepthReached;

            for (IMonsterMove move : participantToControl.getActiveMonster().getMoves()) {
                IBattleState state = battleSimulation.simulateDoMove(participantToControl, move, battleSimulation.getState());

                float util = useAlphaBetaPruning
                        ? minDecision(state, 1, searchDepth, -Float.MAX_VALUE, Float.MAX_VALUE)
                        : minmaxSearch(state, 1, searchDepth);

                if (util > bestMoveUtil) {
                    newBestMove = move;
                    newBestMoveUtil = util;
                }
            }


            for (IMonster monster : participantToControl.getMonsterTeam()) {
                if (monster != participantToControl.getActiveMonster() && monster.getHitPoints() > 0) {
                    IBattleState state = battleSimulation.simulateSwitchMonster(participantToControl, monster, battleSimulation.getState());

                    float util = useAlphaBetaPruning
                            ? minDecision(state, 1, searchDepth, -Float.MAX_VALUE, Float.MAX_VALUE)
                            : minmaxSearch(state, 1, searchDepth);

                    if (util > bestSwitchUtil) {
                        newBestSwitch = monster;
                        newBestSwitchUtil = util;
                    }
                }
            }

            if (maxDepthReached==oldMaxSearchDepth) {
                break;
            }

            if (!outOfTime()) {
                bestMove = newBestMove;
                bestSwitch = newBestSwitch;
                bestMoveUtil = newBestMoveUtil;
                bestSwitchUtil = newBestSwitchUtil;
            }

            searchDepth++;
        }

        System.out.println("searchDepth: " + searchDepth);
        System.out.println("time used: " + ((System.nanoTime()-startTime)/1000000));

        if (bestMoveUtil>=bestSwitchUtil) {
            battleSimulation.doMove(participantToControl, bestMove);
        } else {
            battleSimulation.switchMonster(participantToControl, bestSwitch);
        }
    }

    private float maxDecision(IBattleState battleState, int currentDepth, int maxDepth, float alpha, float beta) {

        if (currentDepth>maxDepthReached) maxDepthReached=currentDepth;

        if (isTerminal(battleState) || currentDepth>=maxDepth || outOfTime()) {
            // dividing the utility of terminal states by the depth of that state in the search
            // will make the AI prefer winning fast over more slowly. If the AI is bound to lose
            // (negative utility) it will prefer doing so slowly
            return utility(battleState)/currentDepth;
            // Alternatively, the below formula would have the AI always prefer the fastest win/lose
            // return utility(battleState)-((float) currentDepth/maxDepth);
        }

        // Save the highest and lowest value encountered until now
        float max = -Float.MAX_VALUE;
        // Search through all the successors of the current state, and find
        // those yielding the highest and the lowest utility
        for (IBattleState state : successorFunction(battleState)) {
            float util = minDecision(state, currentDepth+1, maxDepth, alpha, beta);
            if (util>max) max = util;
            if (max>=beta) {
                //System.out.println("skipped because of beta");
                return max;
            }
            if (max>alpha) alpha = max;
        }

        return max;
    }

    private float minDecision(IBattleState battleState, int currentDepth, int maxDepth, float alpha, float beta) {

        if (currentDepth>maxDepthReached) maxDepthReached=currentDepth;

        if (isTerminal(battleState) || currentDepth>=maxDepth || outOfTime()) {
            // dividing the utility of terminal states by the depth of that state in the search
            // will make the AI prefer winning fast over more slowly. If the AI is bound to lose
            // (negative utility) it will prefer doing so slowly
            return utility(battleState)/currentDepth;
            // Alternatively, the below formula would have the AI always prefer the fastest win/lose
            // return utility(battleState)-((float) currentDepth/maxDepth);
        }

        // Save the highest and lowest value encountered until now
        float min = Float.MAX_VALUE;
        // Search through all the successors of the current state, and find
        // those yielding the highest and the lowest utility
        for (IBattleState state : successorFunction(battleState)) {
            float util = maxDecision(state, currentDepth+1, maxDepth, alpha, beta);
            if (util<min) min = util;
            if (min<=alpha) {
                //System.out.println("Skipped because of alpha");
                return min;
            }
            if (min<beta) beta = min;
        }

        return min;
    }

    private float minmaxSearch(IBattleState battleState, int currentDepth, int maxDepth) {

        if (currentDepth>maxDepthReached) maxDepthReached=currentDepth;

        if (isTerminal(battleState) || currentDepth>=maxDepth || outOfTime()) {
            // dividing the utility of terminal states by the depth of that state in the search
            // will make the AI prefer winning fast over more slowly. If the AI is bound to lose
            // (negative utility) it will prefer doing so slowly
            return utility(battleState)/currentDepth;
            // Alternatively, the below formula would have the AI always prefer the fastest win/lose
            // return utility(battleState)-((float) currentDepth/maxDepth);
        }

        // Save the highest and lowest value encountered until now
        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;
        // Search through all the successors of the current state, and find
        // those yielding the highest and the lowest utility
        for (IBattleState state : successorFunction(battleState)) {
            float util = minmaxSearch(state, currentDepth+1, maxDepth);
            if (util<min) min = util;
            if (util>max) max = util;
        }

        // Find the player whose turn it is
        IBattleParticipant activeParticipant = battleState.isPlayersTurn()
                ? battleState.getPlayer()
                : battleState.getEnemy();
        // If this is the player the AI controls, return the highest possible utility
        // else return the lowest possible utility
        if (activeParticipant.equals(participantToControl)) {
            return max;
        } else {
            return min;
        }

    }

    private boolean isTerminal(IBattleState battleState) {
        IBattleParticipant enemy = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getEnemy()
                : battleState.getPlayer();

        // Check if all the AIs monsters are dead
        boolean allOwnMonstersDead = participantToControl.getMonsterTeam().stream()
                .allMatch(x -> x.getHitPoints()<=0);
        if (allOwnMonstersDead) return true;

        // Check if all the opposing participant's (known) monster are dead
        boolean allEnemyMonstersDead = enemy.getMonsterTeam().stream()
                .filter(x -> knowledgeState.enemyMonsters.contains(x))  //only consider monsters we've seen
                .allMatch(x -> x.getHitPoints()<=0);
        if (allEnemyMonstersDead) return true;

        return false;
    }

    private float utility(IBattleState battleState) {
        /*IBattleParticipant participantToControl = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getPlayer()
                : battleState.getEnemy();

        IBattleParticipant opposingParticipant = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getEnemy()
                : battleState.getPlayer();*/


        int ownMonsterHPSum = 0;
        for(IMonster monster : participantToControl.getMonsterTeam()) {
            if (monster.getHitPoints()>0) ownMonsterHPSum += monster.getHitPoints();
        }

        int enemyMonsterHPSum = 0;
        for(IMonster monster : opposingParticipant.getMonsterTeam()) {
            if (monster.getHitPoints()>0) enemyMonsterHPSum += monster.getHitPoints();
        }

        // This will return 1 if all the enemy's monsters are dead, 0 if all the AI's monster
        // are dead, and a number in between otherwise, which will be higher if the AI's monsters
        // have a larger proportion of the hp of all the monsters in the battle
        return (float)ownMonsterHPSum/(ownMonsterHPSum+enemyMonsterHPSum);
    }

    private List<IBattleState> successorFunction(IBattleState battleState) {

        List<IBattleState> successors = new ArrayList<>();

        IBattleParticipant activeParticipant = getActiveParticipant(battleState);

        for (IMonsterMove move : activeParticipant.getActiveMonster().getMoves()) {
            if (!activeParticipant.equals(participantToControl)) {
                if (!(knowledgeState.getMonsterMoves().containsKey(activeParticipant.getActiveMonster()) &&
                        knowledgeState.getMonsterMoves().get(activeParticipant.getActiveMonster()).contains(move))){
                    continue; // If we have not seen this move, don't consider the option where it is used
                }
            }
            successors.add(battleSimulation.simulateDoMove(activeParticipant, move, battleState));
        }

        if (successors.isEmpty()) {
            // If we don't know any of the monsters moves, add an empty move dealing 0 damage;
            successors.add(battleSimulation.simulateDoMove(activeParticipant, emptyMove, battleState));
        }

        for (IMonster monster : activeParticipant.getMonsterTeam()) {
            if (!activeParticipant.equals(participantToControl)) {
                if (!knowledgeState.getEnemyMonsters().contains(monster)){
                    continue;
                }
            }
            if (monster.equals(activeParticipant.getActiveMonster())) {
                continue;
            }
            if (monster.getHitPoints()<=0) {
                continue;
            }
            successors.add(battleSimulation.simulateSwitchMonster(activeParticipant, monster, battleState));
        }

        return successors;
    }

    private IBattleParticipant getActiveParticipant(IBattleState battleState) {
         return battleState.isPlayersTurn()
                ? battleState.getPlayer() // Return the player, if it is the player's turn
                : battleState.getEnemy(); // Return the enemy, if it is the enemy's turn
    }

    private class KnowledgeState {
        // Those of the enemy's monsters, the AI has seen
        private List<IMonster> enemyMonsters;
        // A map, mapping each of the enemy's monsters to a list of the moves, the AI has seen it use
        private Map<IMonster, List<IMonsterMove>> monsterMoves;

        public KnowledgeState() {
            enemyMonsters = new ArrayList<>();
            monsterMoves = new HashMap<>();
        }

        public List<IMonster> getEnemyMonsters() {
            return enemyMonsters;
        }

        public Map<IMonster, List<IMonsterMove>> getMonsterMoves() {
            return monsterMoves;
        }
    }
}
