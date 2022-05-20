package dk.sdu.mmmi.modulemon.SimpleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAIFactory;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;

public class BattleAIFactory implements IBattleAIFactory {

    public BattleAIFactory() {
    }

    @Override
    public IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        return new BattleAI(battleSimulation, participantToControl);
    }
}
