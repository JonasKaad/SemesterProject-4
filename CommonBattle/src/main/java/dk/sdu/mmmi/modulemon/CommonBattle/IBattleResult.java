package dk.sdu.mmmi.modulemon.CommonBattle;

public interface IBattleResult {
    IBattleParticipant getWinner();
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
}
