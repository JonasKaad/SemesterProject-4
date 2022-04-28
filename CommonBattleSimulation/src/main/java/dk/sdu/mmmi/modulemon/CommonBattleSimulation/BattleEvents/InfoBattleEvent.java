package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

public class InfoBattleEvent implements IBattleEvent {

    private String text;

    public InfoBattleEvent(String text) {
        this.text = text;
    }
    @Override
    public String getText() {
        return text;
    }
}
