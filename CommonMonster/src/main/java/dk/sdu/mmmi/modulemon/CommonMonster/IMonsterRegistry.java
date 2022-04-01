package dk.sdu.mmmi.modulemon.CommonMonster;

import java.util.HashMap;
import java.util.Map;

public interface IMonsterRegistry {
    IMonster getMonster(int ID);
    Map<Integer, IMonster> getAllMonsters();
}