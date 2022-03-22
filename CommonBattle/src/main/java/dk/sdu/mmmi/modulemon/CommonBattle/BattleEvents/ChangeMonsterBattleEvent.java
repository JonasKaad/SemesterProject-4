package dk.sdu.mmmi.modulemon.CommonBattle.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

public class ChangeMonsterBattleEvent implements IBattleEvent{

    private String text;

    private IBattleParticipant participant;

    private IMonster changeTo;

    public ChangeMonsterBattleEvent(String text, IBattleParticipant participant, IMonster changeTo) {
        this.text = text;
        this.participant = participant;
        this.changeTo = changeTo;
    }

    public IBattleParticipant getParticipant() {
        return participant;
    }

    public IMonster getChangeTo() {
        return changeTo;
    }

    @Override
    public String getText() {
        return null;
    }
}
