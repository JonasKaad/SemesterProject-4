package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

public class MonsterFaintChangeBattleEvent extends ChangeMonsterBattleEvent{
    public MonsterFaintChangeBattleEvent(String text, IBattleParticipant participant, IMonster changeTo, IBattleState state) {
        super(text, participant, changeTo, state);
    }
}
