package dk.sdu.mmmi.modulemon.CommonMonster;

import java.util.HashMap;

public interface IMonsterRegistry {
    IMonster getMonster(int ID);
    HashMap<Integer, IMonster> getAllMonsters();
}