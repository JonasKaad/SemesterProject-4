package dk.sdu.mmmi.modulemon.CommonBattle;

public interface IBattleState {
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
    boolean isPlayersTurn();
    IBattleState clone();
}
