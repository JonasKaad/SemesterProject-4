package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MonsterRegistryTest {
    static HashMap<Integer, IMonster> monsters = new HashMap<>();

    @BeforeAll
    static void setupMonsters() {
        monsters.put(0, new Monster("Alpaca",
                MonsterType.GRASS,
                100,
                25,
                60,
                60,
                Arrays.asList(
                        new MonsterMove("Spit", 10, MonsterType.WATER),
                        new MonsterMove("Trample", 25, MonsterType.GRASS)),
                "images/alpaca_1.png",
                "images/alpaca_2.png",
                0));
        monsters.put(1, new Monster("Eel",
                MonsterType.WATER,
                90,
                20,
                70,
                70,
                Arrays.asList(new MonsterMove("Zap", 20, MonsterType.LIGHTNING)),
                "images/eel_1.png",
                "images/eel_2.png",
                1));
    }

    @Test
    void monsterRegistry_getByID_isAccurate() throws IOException, URISyntaxException {
        // Arrange
        IMonster monster1 = monsters.get(0);
        IMonster monster2 = monsters.get(1);

        // Act
        IMonsterRegistry registry = new MonsterRegistry(
                "/json/monsters_test.json",
                "/json/monsters_moves_test.json");
        IMonster monsterToTest1 = registry.getMonster(0);
        IMonster monsterToTest2 = registry.getMonster(1);
        List<IMonsterMove> movesToTest1 = monsterToTest1.getMoves();
        List<IMonsterMove> movesToTest2 = monsterToTest2.getMoves();

        //// Assert
        // Monster 1
        assertEquals(monster1.getName(), monsterToTest1.getName());
        assertEquals(monster1.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(monster1.getDefence(), monsterToTest1.getDefence());
        assertEquals(monster1.getAttack(), monsterToTest1.getAttack());
        assertEquals(monster1.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(monster1.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(monster1.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(monster1.getID(), monsterToTest1.getID());

        // Monster 1 moves
        assertEquals(monster1.getMoves().get(0).getName(), movesToTest1.get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), movesToTest1.get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), movesToTest1.get(0).getType());
        assertEquals(monster1.getMoves().get(1).getName(), movesToTest1.get(1).getName());
        assertEquals(monster1.getMoves().get(1).getDamage(), movesToTest1.get(1).getDamage());
        assertEquals(monster1.getMoves().get(1).getType(), movesToTest1.get(1).getType());

        // Monster 2
        assertEquals(monster2.getName(), monsterToTest2.getName());
        assertEquals(monster2.getMonsterType(), monsterToTest2.getMonsterType());
        assertEquals(monster2.getDefence(), monsterToTest2.getDefence());
        assertEquals(monster2.getAttack(), monsterToTest2.getAttack());
        assertEquals(monster2.getSpeed(), monsterToTest2.getSpeed());
        assertEquals(monster2.getFrontSprite(), monsterToTest2.getFrontSprite());
        assertEquals(monster2.getBackSprite(), monsterToTest2.getBackSprite());
        assertEquals(monster2.getID(), monsterToTest2.getID());
        // Monster 2 moves
        assertEquals(monster2.getMoves().get(0).getName(), movesToTest2.get(0).getName());
        assertEquals(monster2.getMoves().get(0).getDamage(), movesToTest2.get(0).getDamage());
        assertEquals(monster2.getMoves().get(0).getType(), movesToTest2.get(0).getType());

        // Monsters get cloned and are not the same
        assertNotEquals(registry.getMonster(0), registry.getMonster(0));
        assertNotEquals(registry.getMonster(1), registry.getMonster(1));
    }

    @Test
    void monsterRegistry_getHashMap_isAccurate() throws IOException, URISyntaxException {
        // Arrange
        IMonster monster1 = monsters.get(0);
        IMonster monster2 = monsters.get(1);

        // Act
        IMonsterRegistry registry = new MonsterRegistry(
                "/json/monsters_test.json",
                "/json/monsters_moves_test.json");
        Map<Integer, IMonster> monstersToTest = registry.getAllMonsters();
        IMonster monsterToTest1 = monstersToTest.get(0);
        IMonster monsterToTest2 = monstersToTest.get(1);
        List<IMonsterMove> movesToTest1 = monsterToTest1.getMoves();
        List<IMonsterMove> movesToTest2 = monsterToTest2.getMoves();

        //// Assert
        // Monster 1
        assertEquals(monster1.getName(), monsterToTest1.getName());
        assertEquals(monster1.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(monster1.getDefence(), monsterToTest1.getDefence());
        assertEquals(monster1.getAttack(), monsterToTest1.getAttack());
        assertEquals(monster1.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(monster1.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(monster1.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(monster1.getID(), monsterToTest1.getID());

        // Monster 1 moves
        assertEquals(monster1.getMoves().get(0).getName(), movesToTest1.get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), movesToTest1.get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), movesToTest1.get(0).getType());
        assertEquals(monster1.getMoves().get(1).getName(), movesToTest1.get(1).getName());
        assertEquals(monster1.getMoves().get(1).getDamage(), movesToTest1.get(1).getDamage());
        assertEquals(monster1.getMoves().get(1).getType(), movesToTest1.get(1).getType());

        // Monster 2
        assertEquals(monster2.getName(), monsterToTest2.getName());
        assertEquals(monster2.getMonsterType(), monsterToTest2.getMonsterType());
        assertEquals(monster2.getDefence(), monsterToTest2.getDefence());
        assertEquals(monster2.getAttack(), monsterToTest2.getAttack());
        assertEquals(monster2.getSpeed(), monsterToTest2.getSpeed());
        assertEquals(monster2.getFrontSprite(), monsterToTest2.getFrontSprite());
        assertEquals(monster2.getBackSprite(), monsterToTest2.getBackSprite());
        assertEquals(monster2.getID(), monsterToTest2.getID());
        // Monster 2 moves
        assertEquals(monster2.getMoves().get(0).getName(), movesToTest2.get(0).getName());
        assertEquals(monster2.getMoves().get(0).getDamage(), movesToTest2.get(0).getDamage());
        assertEquals(monster2.getMoves().get(0).getType(), movesToTest2.get(0).getType());

        // Monsters get cloned and are not the same
        assertNotEquals(registry.getMonster(0), registry.getMonster(0));
        assertNotEquals(registry.getMonster(1), registry.getMonster(1));
    }
}
