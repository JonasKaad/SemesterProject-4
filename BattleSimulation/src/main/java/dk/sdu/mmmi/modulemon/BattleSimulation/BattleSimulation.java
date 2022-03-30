package dk.sdu.mmmi.modulemon.BattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattle.BattleEvents.*;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import java.util.Optional;

public class BattleSimulation implements IBattleSimulation {

    private IBattleParticipant player;
    private IBattleParticipant enemy;

    private IBattleParticipant activeParticipant;

    private IBattleEvent nextEvent;
    private Runnable onNextEvent;

    //"Mocked" enemyControlSystem. Should be moved somewhere else and hidden behind an interface
    private TempEnemyControlSystem ecs = new TempEnemyControlSystem();

    @Override
    public void StartBattle(IBattleParticipant player, IBattleParticipant enemy) {
        this.player = player;
        this.enemy = enemy;

        if (player.getActiveMonster().getHitPoints()<=0 || enemy.getActiveMonster().getHitPoints()<=0){
            throw new RuntimeException("Active monsters should have at least 1 HP");
        }

        IBattleParticipant firstToTakeTurn = assignFirstTurn();

        if (!firstToTakeTurn.isPlayerControlled()) {
            nextEvent = new InfoBattleEvent("enemy starts the battle");
            onNextEvent = () -> {
                activeParticipant = firstToTakeTurn;
                ecs.doAction(activeParticipant, this);
            };
        } else {
            nextEvent = new InfoBattleEvent("player starts the battle");
            onNextEvent = () -> {
                activeParticipant = firstToTakeTurn;
            };
        }

    }

    private IBattleParticipant assignFirstTurn() {
        // Player will start if the speeds are equal
        if (player.getActiveMonster().getSpeed()>=enemy.getActiveMonster().getSpeed()){
            return player;
        } else {
            return enemy;
        }
    }

    private void switchTurns() {
        if (activeParticipant == player) {
            activeParticipant = enemy;
            ecs.doAction(enemy, this);
        } else {
            activeParticipant = player;
        }

    }

    private String getActiveParticipantTitle() {
        if (activeParticipant.isPlayerControlled()) {
            return "player";
        } else {
            return "enemy";
        }
    }

    @Override
    public IBattleParticipant getPlayer() {
        return this.player;
    }

    @Override
    public IBattleParticipant getEnemy() {
        return this.enemy;
    }

    @Override
    public boolean isPlayersTurn() {
        return activeParticipant==player;
    }

    @Override
    public void doMove(IBattleParticipant battleParticipant, IMonsterMove move) {
        if (battleParticipant!=activeParticipant) {
            throw new IllegalArgumentException("It is not that battle participants turn!");
        }
        if (!battleParticipant.getActiveMonster().getMoves().contains(move)) {
            throw new IllegalArgumentException("The battle participants active monster can't use the specified move");
        }

        IMonster source = battleParticipant.getActiveMonster();
        IMonster target;
        String participantTitle;
        String opposingParticipantTitle;
        IBattleParticipant opposingParticipant;

        if (battleParticipant.isPlayerControlled()) {
            target = enemy.getActiveMonster();
            participantTitle = "player";
            opposingParticipantTitle = "enemy";
            opposingParticipant = enemy;
        } else {
            target = player.getActiveMonster();
            participantTitle = "enemy";
            opposingParticipantTitle = "player";
            opposingParticipant = player;
        }

        float moveDamage = (float) move.getDamage();
        float sourceAttack = (float) source.getAttack();
        float targetDefence = (float) target.getDefence();

        int damage = Math.round(moveDamage*(sourceAttack/targetDefence));


        nextEvent = new MoveBattleEvent(participantTitle + " monster used " + move.getName() + " for " + damage + " damage", battleParticipant, move, damage);


        onNextEvent = () -> {
            int newHitPoints = target.getHitPoints()-damage;
            if (newHitPoints>0) {
                target.setHitPoints(newHitPoints);

                switchTurns();


            } else {
                target.setHitPoints(0);
                Optional<IMonster> nextMonster = opposingParticipant.getMonsterTeam().stream().filter(x -> x.getHitPoints() > 0).findFirst();

                if (nextMonster.isPresent()) {
                    nextEvent = new ChangeMonsterBattleEvent(opposingParticipantTitle + "s monster fainted... Changed to " + nextMonster.get().getName(), opposingParticipant, nextMonster.get());
                    onNextEvent = () -> {
                        opposingParticipant.setActiveMonster(nextMonster.get());
                        switchTurns();
                    };
                } else {
                    nextEvent = new VictoryBattleEvent(opposingParticipantTitle + "s monster fainted... " + participantTitle + " won the battle.", battleParticipant);
                }
            }
        };



    }

    @Override
    public void switchMonster(IBattleParticipant battleParticipant, IMonster monster) {
        if (activeParticipant!=battleParticipant) {
            throw new IllegalArgumentException("It is not that battle participants turn!");
        }
        if (battleParticipant.getMonsterTeam().contains(monster)) {
            nextEvent = new ChangeMonsterBattleEvent(getActiveParticipantTitle()+" changed monster to " + monster.getName(), battleParticipant, monster);
            onNextEvent = () -> {
                battleParticipant.setActiveMonster(monster);
                switchTurns();
            };
        } else {
            throw new IllegalArgumentException("Can't change a players monster to a monster which is not in their team");
        }
    }

    @Override
    public void runAway(IBattleParticipant battleParticipant) {
        if (battleParticipant!=activeParticipant) {
            throw new IllegalArgumentException("It is not that battle participants turn!");
        }

        //Here some randomness could be introduced, so the player can't always run away
        nextEvent = new RunAwayBattleEvent(getActiveParticipantTitle() + " ran away", battleParticipant);

    }

    @Override
    public IBattleEvent getNextBattleEvent() {
        IBattleEvent event = nextEvent;
        if (event!=null && onNextEvent!=null) {
            nextEvent = null;
            onNextEvent.run();
        }
        return event;
    }

    //Should be moved into another component at some point
    private class TempEnemyControlSystem {
        void doAction(IBattleParticipant enemy, IBattleSimulation simulation) {
            simulation.doMove(enemy, enemy.getActiveMonster().getMoves().get(0));
        }
    }
}
