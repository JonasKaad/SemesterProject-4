package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

public class ChangeMonsterBattleEvent implements IBattleEvent {

    private String text;

    private IBattleParticipant participant;

    private IMonster changeTo;

    private IBattleState battleState;

    public ChangeMonsterBattleEvent(String text, IBattleParticipant participant, IMonster changeTo, IBattleState state) {
        this.text = text;
        this.participant = participant;
        this.changeTo = changeTo;
        this.battleState = state;
    }

    public IBattleParticipant getParticipant() {
        return participant;
    }

    public IMonster getChangeTo() {
        return changeTo;
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
