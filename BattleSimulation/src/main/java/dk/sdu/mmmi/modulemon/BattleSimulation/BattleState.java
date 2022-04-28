package dk.sdu.mmmi.modulemon.BattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;

public class BattleState implements IBattleState {

    private IBattleParticipant player;
    private IBattleParticipant enemy;
    private IBattleParticipant activeParticipant;

    public BattleState(IBattleParticipant player, IBattleParticipant enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public IBattleParticipant getPlayer() {
        return player;
    }

    @Override
    public IBattleParticipant getEnemy() {
        return enemy;
    }

    @Override
    public boolean isPlayersTurn() {
        return activeParticipant.equals(player);
    }

    public IBattleParticipant getActiveParticipant() {
        return this.activeParticipant;
    }

    public void setActiveParticipant(IBattleParticipant participant) {
        this.activeParticipant = participant;
    }

    @Override
    public IBattleState clone() {
        IBattleParticipant playerClone = player.clone();
        IBattleParticipant enemyClone = enemy.clone();
        IBattleParticipant activeParticipant = this.isPlayersTurn() ? playerClone : enemyClone;
        BattleState clone = new BattleState(playerClone, enemyClone);
        clone.setActiveParticipant(activeParticipant);
        return clone;
    }
}
