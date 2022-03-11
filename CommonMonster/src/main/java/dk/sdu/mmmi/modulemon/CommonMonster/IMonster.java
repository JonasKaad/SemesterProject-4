package dk.sdu.mmmi.modulemon.CommonMonster;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public interface IMonster {

    String getName();
    int getHitPoints();
    int getAttack();
    int getDefence();
    int getSpeed();
    Texture getSprite();
    MonsterType getMonsterType();
    ArrayList<IMonsterMove> getMoves();

}
