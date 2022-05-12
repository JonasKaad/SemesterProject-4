package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAIFactory;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.common.services.IGameSettings;

public class BattleAIFactory implements IBattleAIFactory {

    IGameSettings settings = null;

    public BattleAIFactory() {

    }

    @Override
    public IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        return new BattleAI(battleSimulation, participantToControl, this.settings);
    }

    public void setSettingsService(IGameSettings settings) {
        System.out.println("Settings injected into AIFactory");
        this.settings = settings;
        if (settings.getSetting("AI processing time")==null) {
            settings.setSetting("AI processing time", 1000);
        }
        if (settings.getSetting("AI alpha-beta pruning")==null) {
            settings.setSetting("AI alpha-beta pruning", true);
        }
    }

    public void removeSettingsService(IGameSettings settings) {
        this.settings = null;
    }
}
