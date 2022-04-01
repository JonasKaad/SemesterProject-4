package dk.sdu.mmmi.modulemon.CommonBattle.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;

public class RunAwayBattleEvent implements IBattleEvent {
    private String text;
    private IBattleParticipant participant;

    public RunAwayBattleEvent(String text, IBattleParticipant participant) {
        this.text = text;
        this.participant = participant;
    }

    @Override
    public String getText() {
        return text;
    }

    public IBattleParticipant getParticipant() {
        return participant;
    }
}
