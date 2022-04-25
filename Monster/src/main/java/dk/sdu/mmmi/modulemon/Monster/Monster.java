package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Monster implements IMonster {
    String name;
    MonsterType type;
    int maxHitPoints;
    int hitPoints;
    int defence;
    int attack;
    int speed;
    String frontSprite;
    String backSprite;
    List<IMonsterMove> moves;
    int ID;
    UUID uuid;

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite, int ID) {
        this.name = name;
        this.type = type;
        this.maxHitPoints = hitPoints;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.moves = moves;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
        this.ID = ID;
        this.uuid = UUID.randomUUID();
    }

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite, int ID, UUID uuid) {
        this(name, type, hitPoints, defence, attack, speed, moves, frontSprite, backSprite, ID);
        this.uuid = uuid;
    }

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves) {
        this(name, type, hitPoints,defence, attack,speed,moves, "", "", 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MonsterType getMonsterType() {
        return type;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    @Override
    public void setHitPoints(int hitPoint) {
        hitPoints = hitPoint;
    }

    @Override
    public List<String> getStats() {
        List<String> statList = new ArrayList<>();
        statList.add("Attack: " + this.getAttack());
        statList.add("Defence: " + this.getDefence());
        statList.add("Speed: " + this.getSpeed());
        return statList;
    }

    public int getDefence() {
        return defence;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String getFrontSprite() {
        return frontSprite;
    }

    @Override
    public String getBackSprite() {
        return backSprite;
    }

    @Override
    public List<IMonsterMove> getMoves() {
        return moves;
    }

    @Override
    public int getID() {
        return ID;
    }

    // Only for testing purposes
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IMonster clone() {
        return new Monster(this.name, this.type, this.hitPoints, this.defence, this.attack, this.speed, this.moves, this.frontSprite, this.backSprite, this.ID, this.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Monster) {
            return ((Monster) o).uuid.equals(this.uuid);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
