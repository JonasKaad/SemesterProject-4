package dk.sdu.mmmi.modulemon.Monster;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.common.data.Entity;

import java.util.List;

public class Monster extends Entity implements IMonster {
    String name;
    MonsterType type;
    int hitPoints;
    int defence;
    int attack;
    int speed;
    String frontSprite;
    String backSprite;
    List<IMonsterMove> moves;

    // For some reason using Libgdx gives a null pointer on the texture, uncommented for now
    /*
    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed, Texture sprite, List<IMonsterMove> moves) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.sprite = sprite;
        this.moves = moves;
    }*/

    public Monster(String name, MonsterType type, int hitPoints, int defence, int attack, int speed,  List<IMonsterMove> moves, String frontSprite, String backSprite) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.defence = defence;
        this.attack = attack;
        this.speed = speed;
        this.moves = moves;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
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


    // Only for testing purposes
    public void setName(String name) {
        this.name = name;
    }
}
