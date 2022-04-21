package dk.sdu.mmmi.modulemon.BattleSceneMock;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

public class MockMonsterMove implements IMonsterMove {

    private String _name;
    private MonsterType _type;
    private int _damage;
    private String soundPath;

    public MockMonsterMove(String _name, MonsterType _type, int _damage, String _soundPath) {
        this._name = _name;
        this._type = _type;
        this._damage = _damage;
        this.soundPath = _soundPath;
    }

    public MockMonsterMove(String _name, MonsterType _type, int _damage) {
        this._name = _name;
        this._type = _type;
        this._damage = _damage;
        this.soundPath = "/sounds/Tackle.ogg";
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static MockMonsterMove getDefaultMonsterMove(){
        return new MockMonsterMove("Blow", MonsterType.AIR, 0);
    }


    @Override
    public String getName() {
        return _name;
    }

    @Override
    public int getDamage() {
        return _damage;
    }

    @Override
    public MonsterType getType() {
        return _type;
    }

    @Override
    public String getSoundPath() {
        return soundPath;
    }
}
