package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;

import java.io.IOException;
import java.net.URISyntaxException;

public class MonsterRegistry implements IMonsterRegistry {
    private IMonster[] monsters;

    public MonsterRegistry() throws IOException, URISyntaxException {
        monsters = MonsterParser.parseMonsters(
                "/json/monsters.json",
                "/json/moves.json"
        );
    }

    public MonsterRegistry(String monstersPath, String movesPath) throws IOException, URISyntaxException {
        monsters = MonsterParser.parseMonsters(
                monstersPath,
                movesPath
        );
    }

    @Override
    public IMonster getMonster(int ID) {
        return ((Monster) monsters[ID]).copy();
    }

    @Override
    public IMonster[] getAllMonsters() {
        IMonster[] copiedMonsters = new IMonster[monsters.length];
        for (IMonster monster : monsters) {
            copiedMonsters[monster.getID()] = monster.clone();
        }
        return copiedMonsters;
    }

    @Override
    public int getMonsterAmount() {
        return monsters.length;
    }
}