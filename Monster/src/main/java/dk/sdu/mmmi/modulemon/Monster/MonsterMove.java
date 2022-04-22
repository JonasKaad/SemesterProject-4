package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

public class MonsterMove implements IMonsterMove {

    private String name;
    private int damage;
    private MonsterType type;
    private String soundPath;

    public MonsterMove(String name, int damage, MonsterType type, String soundPath) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.soundPath = soundPath;
    }

    public MonsterMove(String name, int damage, MonsterType type) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.soundPath = "sounds/tackle.ogg";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public MonsterType getType() {
        return type;
    }

    @Override
    public String getSoundPath() {
        return soundPath;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
