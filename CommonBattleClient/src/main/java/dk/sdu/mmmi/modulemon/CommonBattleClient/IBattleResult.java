package dk.sdu.mmmi.modulemon.CommonBattleClient;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;

public interface IBattleResult {
    IBattleParticipant getWinner();
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
}
