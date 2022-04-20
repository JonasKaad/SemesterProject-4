package dk.sdu.mmmi.modulemon.CommonBattle;

public interface IBattleAIFactory {
    IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl);
}

