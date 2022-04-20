package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleAIFactory;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;

public class BattleAIFactory implements IBattleAIFactory {

    public BattleAIFactory() {

    }

    @Override
    public IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        return new BattleAI(battleSimulation, participantToControl);
    }
}
