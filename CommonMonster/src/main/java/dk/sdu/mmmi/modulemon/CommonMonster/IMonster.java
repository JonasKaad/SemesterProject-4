package dk.sdu.mmmi.modulemon.CommonMonster;

import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public interface IMonster {

    String getName();
    int getHitPoints();
    int setHitPoint();
    int getAttack();
    int getDefence();
    int getSpeed();
    Texture getSprite();
    MonsterType getMonsterType();
    List<IMonsterMove> getMoves();
    int takeDamage();
}
