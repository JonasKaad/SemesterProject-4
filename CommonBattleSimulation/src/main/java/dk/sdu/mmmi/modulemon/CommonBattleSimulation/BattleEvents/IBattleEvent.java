package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;

public interface IBattleEvent {
    String getText();
    IBattleState getState();
}
