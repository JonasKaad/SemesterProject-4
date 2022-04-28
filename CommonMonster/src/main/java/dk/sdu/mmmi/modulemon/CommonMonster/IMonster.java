package dk.sdu.mmmi.modulemon.CommonMonster;

import java.util.List;
import java.util.Map;

public interface IMonster {
    String getName();
    int getMaxHitPoints();
    int getHitPoints();
    void setHitPoints(int hitPoint);
    String getFrontSprite();
    String getBackSprite();
    MonsterType getMonsterType();
    List<IMonsterMove> getMoves();
    // This function's purpose is to give the caller a good way to display whatever stats there are.
    List<String> getStats();
    IMonster clone();
    int getID();

}
