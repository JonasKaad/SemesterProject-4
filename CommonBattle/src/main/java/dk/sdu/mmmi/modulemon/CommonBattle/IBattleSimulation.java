package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleSimulation {
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
    boolean isPlayersTurn();
    void doMove(IMonsterMove move);
    void switchMonster(IMonster monster);
    void runAway();
}
