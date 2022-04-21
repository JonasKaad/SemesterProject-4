package dk.sdu.mmmi.modulemon.CommonBattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;

public interface IBattleState {
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
    boolean isPlayersTurn();
    IBattleState clone();
}
