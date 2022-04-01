package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.List;

public class Monster implements IMonster {
    String name;
    MonsterType type;
    int hitPoints;
    int defence;
    int attack;
    int speed;
    String frontSprite;
    String backSprite;
    List<IMonsterMove> moves;
    int ID;

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite, int ID) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.moves = moves;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
        this.ID = ID;
    }
    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.moves = moves;
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
    public void setHitPoints(int hitPoint) {
        hitPoints = hitPoint;
    }

    @Override
    public int getDefence() {
        return defence;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
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
        return new Monster(this.name, this.type, this.hitPoints, this.defence, this.attack, this.speed, this.moves);

    }
}
