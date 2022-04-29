package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;

public class InfoBattleEvent implements IBattleEvent {

    private String text;

    private IBattleState battleState;

    public InfoBattleEvent(String text, IBattleState state) {
        this.text = text;
        this.battleState = state;
    }
    @Override
    public String getText() {
        return text;
    }

    @Override
    public IBattleState getState() {
        return this.battleState;
    }


}
