package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public class MonsterRegistry implements IMonsterRegistry {
    HashMap<Integer, IMonster> monsters;

    public MonsterRegistry() throws IOException, URISyntaxException {
        monsters = new HashMap<>();
        List<IMonster> monstersList = MonsterParser.parseMonsters(
                "/json/monsters.json",
                "/json/moves.json"
        );
        for(IMonster monster : monstersList) {
            monsters.put(monster.getID(), monster);
        }
    }

    public MonsterRegistry(String monstersPath, String movesPath) throws IOException, URISyntaxException {
        monsters = new HashMap<>();
        List<IMonster> monstersList = MonsterParser.parseMonsters(
                monstersPath,
                movesPath
        );
        for(IMonster monster : monstersList) {
            monsters.put(monster.getID(), monster);
        }
    }

    @Override
    public IMonster getMonster(int ID) {
        return monsters.get(ID).clone();
    }

    @Override
    public HashMap<Integer, IMonster> getAllMonsters() {
        HashMap<Integer, IMonster> copiedMonsters = new HashMap<>();
        for (HashMap.Entry<Integer, IMonster> entry : monsters.entrySet()) {
            copiedMonsters.put(entry.getKey(), entry.getValue().clone());
        }
        return copiedMonsters;
    }
}