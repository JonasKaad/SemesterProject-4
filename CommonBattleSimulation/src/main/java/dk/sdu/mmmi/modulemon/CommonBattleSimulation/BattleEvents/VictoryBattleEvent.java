package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;

public class VictoryBattleEvent implements IBattleEvent {
    private String text;
    private IBattleParticipant winner;
    private IBattleState battleState;

    public VictoryBattleEvent(String text, IBattleParticipant winner, IBattleState state) {
        this.text = text;
        this.winner = winner;
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

    public IBattleParticipant getWinner() {
        return winner;
    }
}
