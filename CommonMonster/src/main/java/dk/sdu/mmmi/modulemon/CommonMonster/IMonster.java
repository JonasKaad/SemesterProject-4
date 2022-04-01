package dk.sdu.mmmi.modulemon.CommonMonster;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public interface IMonster {
    String getName();
    int getHitPoints();
    void setHitPoints(int hitPoint);
    int getAttack();
    int getDefence();
    int getSpeed();
    Texture getFrontSprite();
    Texture getBackSprite();
    MonsterType getMonsterType();
    List<IMonsterMove> getMoves();
    IMonster clone();
}
