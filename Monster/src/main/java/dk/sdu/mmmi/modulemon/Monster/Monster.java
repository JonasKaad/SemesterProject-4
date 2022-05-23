package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Monster implements IMonster {
    private String name;
    private MonsterType type;
    private int maxHitPoints;
    private int hitPoints;
    private int defence;
    private int attack;
    private int speed;
    private String frontSprite;
    private String backSprite;
    private List<IMonsterMove> moves;
    private int ID;
    private UUID uuid;

    public Monster(String name, MonsterType type, int hitPoints, int maxHitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite, int ID, UUID uuid) {
        this.name = name;
        this.type = type;
        this.maxHitPoints = maxHitPoints;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.moves = moves;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
        this.ID = ID;
        this.uuid = uuid;
    }

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite, int ID) {
        this(name, type, hitPoints, hitPoints, defence, attack, speed, moves, frontSprite, backSprite, ID, UUID.randomUUID());
    }

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves) {
        this(name, type, hitPoints,defence, attack,speed,moves, "", "", 0);
    }

    @Override
    public String getName() {
        return name;
    }

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
        String monsterType = String.valueOf(getMonsterType()).toLowerCase(Locale.ROOT);
        String upperCaseMoveType = monsterType.substring(0, 1).toUpperCase() + monsterType.substring(1);
        statList.add("Type: " + upperCaseMoveType);
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
        return new Monster(this.name, this.type, this.hitPoints, this.maxHitPoints, this.defence, this.attack, this.speed, this.moves, this.frontSprite, this.backSprite, this.ID, this.uuid);
    }

    public IMonster copy() {
        return new Monster(this.name, this.type, this.hitPoints, this.maxHitPoints, this.defence, this.attack, this.speed, this.moves, this.frontSprite, this.backSprite, this.ID, UUID.randomUUID());
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
