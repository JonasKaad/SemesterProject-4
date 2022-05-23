package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAIFactory;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.common.SettingsRegistry;
import dk.sdu.mmmi.modulemon.common.services.IGameSettings;

public class BattleAIFactory implements IBattleAIFactory {

    private IGameSettings settings = null;

    public BattleAIFactory() {

    }

    @Override
    public IBattleAI getBattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        return new BattleAI(battleSimulation, participantToControl, this.settings);
    }

    public void setSettingsService(IGameSettings settings) {
        System.out.println("Settings injected into AIFactory");
        this.settings = settings;
        if (settings.getSetting(SettingsRegistry.getInstance().getAIProcessingTimeSetting())==null) {
            settings.setSetting(SettingsRegistry.getInstance().getAIProcessingTimeSetting(), 1000);
        }
        if (settings.getSetting(SettingsRegistry.getInstance().getAIAlphaBetaSetting())==null) {
            settings.setSetting(SettingsRegistry.getInstance().getAIAlphaBetaSetting(), true);
        }
    }

    public void removeSettingsService(IGameSettings settings) {
        this.settings = null;
    }
}
