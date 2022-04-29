package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;

public class RunAwayBattleEvent implements IBattleEvent {
    private String text;
    private IBattleParticipant participant;
    private IBattleState battleState;

    public RunAwayBattleEvent(String text, IBattleParticipant participant, IBattleState state) {
        this.text = text;
        this.participant = participant;
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

    public IBattleParticipant getParticipant() {
        return participant;
    }
}
