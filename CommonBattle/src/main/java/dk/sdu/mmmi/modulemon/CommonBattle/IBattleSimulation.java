package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleSimulation {
    void StartBattle(IBattleParticipant player, IBattleParticipant enemy);
    IBattleParticipant getPlayer();
    IBattleParticipant getEnemy();
    boolean isPlayersTurn();
    void doMove(IBattleParticipant battleParticipant, IMonsterMove move);
    void switchMonster(IBattleParticipant battleParticipant, IMonster monster);
    void runAway(IBattleParticipant battleParticipant);

    /**
     *
     * @return The next battle event to be shown.
     * Returns `null` if there is no more battle events
     */
    IBattleEvent getNextBattleEvent();
}
