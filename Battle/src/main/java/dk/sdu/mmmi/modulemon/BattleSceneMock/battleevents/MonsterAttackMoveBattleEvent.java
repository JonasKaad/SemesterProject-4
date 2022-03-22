package dk.sdu.mmmi.modulemon.BattleSceneMock.battleevents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class MonsterAttackMoveBattleEvent implements IBattleEvent {

    private IMonster _moveUser;
    private IMonster _attackedMonster;
    private IMonsterMove _usedMove;
    private IBattleParticipant _monsterOwner;

    public MonsterAttackMoveBattleEvent(IMonster _moveUser, IMonster _attackedMonster, IMonsterMove _usedMove, IBattleParticipant _monsterOwner) {
        this._moveUser = _moveUser;
        this._attackedMonster = _attackedMonster;
        this._usedMove = _usedMove;
        this._monsterOwner = _monsterOwner;
    }

    @Override
    public String getText() {
        return String.format("%s used %s. It was pretty \"meh\".", _moveUser.getName(), _usedMove.getName());
    }

    public int getDamageDealt() {
        return _usedMove.getDamage();
    }

    public IMonster getAttackedMonster() {
        return _attackedMonster;
    }

    public IMonster getMoveUser() {
        return _moveUser;
    }

    public IMonsterMove getUsedMove() {
        return _usedMove;
    }

    public IBattleParticipant getMonsterOwner() {
        return _monsterOwner;
    }
}
