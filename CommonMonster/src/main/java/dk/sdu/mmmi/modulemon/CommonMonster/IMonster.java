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
    List<IMonsterMove> getMoves();
    List<String> getStats();
    IMonster clone();
    int getID();
}
