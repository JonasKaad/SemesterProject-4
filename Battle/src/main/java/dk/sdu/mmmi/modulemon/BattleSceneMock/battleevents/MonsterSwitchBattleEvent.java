package dk.sdu.mmmi.modulemon.BattleSceneMock.battleevents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

public class MonsterSwitchBattleEvent implements IBattleEvent {
    private IBattleParticipant _monsterOwner;
    private IMonster _newMonster;

    public MonsterSwitchBattleEvent(IBattleParticipant _monsterOwner, IMonster _newMonster) {
        this._monsterOwner = _monsterOwner;
        this._newMonster = _newMonster;
    }

    @Override
    public String getText() {
        return String.format("%s swtiched in %s");
    }
}
