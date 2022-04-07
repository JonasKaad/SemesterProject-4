package dk.sdu.mmmi.modulemon.CommonMonster;

import java.util.HashMap;
import java.util.Map;

public interface IMonsterRegistry {
    /**
     * Pre-condition: The registry contains monsters from id 0 to monsterAmount - 1. There should be no id's between
     * 0 and monsterAmount - 1 that don't map to a monster
     */
    IMonster getMonster(int ID);
    IMonster[] getAllMonsters();
    int getMonsterAmount();
}