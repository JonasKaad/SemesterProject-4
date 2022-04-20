package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleState;
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

    @Override
    public void doAction(IBattleSimulation b) {

        // Update state, should the enemy have changed their monster
        if (!knowledgeState.getEnemyMonsters().contains(opposingParticipant.getActiveMonster())) {
            knowledgeState.getEnemyMonsters().add(opposingParticipant.getActiveMonster());
        }

        int searchDepth = 10;

        // Try minmax on all available actions here
        IMonsterMove bestMove = null;
        float bestMoveUtil = -Float.MAX_VALUE;
        for (IMonsterMove move : participantToControl.getActiveMonster().getMoves()) {
            IBattleState state = battleSimulation.simulateDoMove(participantToControl, move, battleSimulation.getState());
            float util = minmaxSearch(state, 1, searchDepth);
            if (util>bestMoveUtil) {
                bestMove = move;
                bestMoveUtil = util;
            }
        }

        IMonster bestSwitch = null;
        float bestSwitchUtil = -Float.MAX_VALUE;
        for (IMonster monster : participantToControl.getMonsterTeam()) {
            if (monster != participantToControl.getActiveMonster()) {
                IBattleState state = battleSimulation.simulateSwitchMonster(participantToControl, monster, battleSimulation.getState());
                float util = minmaxSearch(state, 1, searchDepth);
                if (util>bestSwitchUtil) {
                    bestSwitch = monster;
                    bestSwitchUtil = util;
                }
            }
        }

        if (bestMoveUtil>=bestSwitchUtil) {
            battleSimulation.doMove(participantToControl, bestMove);
        } else {
            battleSimulation.switchMonster(participantToControl, bestSwitch);
        }
    }

    private float minmaxSearch(IBattleState battleState, int currentDepth, int maxDepth) {
        if (isTerminal(battleState) || currentDepth>=maxDepth) {
            System.out.println("maximum depth reached (AI)");
            // dividing the utility of terminal states by the depth of that state in the search
            // will make the AI prefer winning fast over more slowly. If the AI is bound to lose
            // (negative utility) it will prefer doing so slowly
            return utility(battleState)/currentDepth;
            // Alternatively, the below formula would have the AI always prefer the fastest win/lose
            // return utility(battleState)-((float) currentDepth/maxDepth);
        }

        // Save the highest and lowest value encountered until now
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
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
        IBattleParticipant participantToControl = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getPlayer()
                : battleState.getEnemy();

        // Check if all the AIs monsters are dead
        boolean allOwnMonstersDead = participantToControl.getMonsterTeam().stream()
                .allMatch(x -> x.getHitPoints()<=0);
        if (allOwnMonstersDead) return true;

        // Check if all the opposing participant's (known) monster are dead
        boolean allEnemyMonstersDead = participantToControl.getMonsterTeam().stream()
                .filter(x -> knowledgeState.enemyMonsters.contains(x))  //only consider monsters we've seen
                .allMatch(x -> x.getHitPoints()<=0);
        if (allEnemyMonstersDead) return true;

        return false;
    }

    private float utility(IBattleState battleState) {
        IBattleParticipant participantToControl = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getPlayer()
                : battleState.getEnemy();

        IBattleParticipant opposingParticipant = battleState.getPlayer().equals(this.participantToControl)
                ? battleState.getEnemy()
                : battleState.getPlayer();

        // Check if all the AIs monsters are dead
        boolean allOwnMonstersDead = participantToControl.getMonsterTeam().stream()
                .allMatch(x -> x.getHitPoints()<=0);
        if (allOwnMonstersDead) return -1;

        // Check if all the opposing participant's (known) monster are dead
        boolean allEnemyMonstersDead = opposingParticipant.getMonsterTeam().stream()
                .filter(x -> knowledgeState.enemyMonsters.contains(x))  //only consider monsters we've seen
                .allMatch(x -> x.getHitPoints()<=0);
        if (allEnemyMonstersDead) return 1;

        return 0;
    }

    //TODO write comments
    private List<IBattleState> successorFunction(IBattleState battleState) {

        List<IBattleState> successors = new ArrayList<>();

        IBattleParticipant activeParticipant = getActiveParticipant(battleState);

        for (IMonsterMove move : activeParticipant.getActiveMonster().getMoves()) {
            if (!activeParticipant.equals(participantToControl)) {
                if (!knowledgeState.getMonsterMoves().get(activeParticipant.getActiveMonster()).contains(move)){
                    break; // If we have not seen this move, don't consider the option where it is used
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
                    break;
                }
            }
            if (monster.equals(activeParticipant.getActiveMonster())) {
                break;
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
