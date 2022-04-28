package dk.sdu.mmmi.modulemon.CommonBattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents.IBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleSimulation {
    void StartBattle(IBattleParticipant player, IBattleParticipant enemy);

    IBattleState getState();

    void doMove(IBattleParticipant battleParticipant, IMonsterMove move);

    void switchMonster(IBattleParticipant battleParticipant, IMonster monster);

    void runAway(IBattleParticipant battleParticipant);

    IBattleState simulateDoMove(IBattleParticipant participant, IMonsterMove move, IBattleState currentState);

    IBattleState simulateSwitchMonster(IBattleParticipant participant, IMonster monster, IBattleState currentState);
    /**
     * @return The next battle event to be shown.
     * Returns `null` if there is no more battle events
     */
    IBattleEvent getNextBattleEvent();
}
