package dk.sdu.mmmi.modulemon.CommonBattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;

public interface IBattleAIFactory {
    IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl);
}

