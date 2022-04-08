package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterRegistryTest {
    static IMonster[] monsters;
    static Monster monster0;
    static Monster monster1;
    static IMonsterRegistry registry;

    @BeforeAll
    static void setupMonsters() throws IOException, URISyntaxException {
        monsters = new IMonster[2];
        monsters[0] = new Monster("Alpaca",
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
                0);
        monsters[1] = new Monster("Eel",
                MonsterType.WATER,
                90,
                20,
                70,
                70,
                Arrays.asList(new MonsterMove("Zap", 20, MonsterType.LIGHTNING)),
                "images/eel_1.png",
                "images/eel_2.png",
                1);
        monster0 = (Monster) monsters[0];
        monster1 = (Monster) monsters[1];

        registry = new MonsterRegistry(
                "/json/monsters_test.json",
                "/json/monsters_moves_test.json");
    }

    @Test
    @Order(1)
    void monsterRegistry_amountOfMonsters_isAccurate() {
        assertEquals(2, registry.getMonsterAmount());
    }

    @Test
    @Order(2)
    void monsterRegistry_getByID_isAccurate() {
        Monster monsterToTest1 = (Monster) registry.getMonster(0);
        Monster monsterToTest2 = (Monster) registry.getMonster(1);
        List<IMonsterMove> movesToTest1 = monsterToTest1.getMoves();
        List<IMonsterMove> movesToTest2 = monsterToTest2.getMoves();

        //// Assert
        // Monster 1
        assertEquals(monster0.getName(), monsterToTest1.getName());
        assertEquals(monster0.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(monster0.getDefence(), monsterToTest1.getDefence());
        assertEquals(monster0.getAttack(), monsterToTest1.getAttack());
        assertEquals(monster0.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(monster0.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(monster0.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(monster0.getID(), monsterToTest1.getID());

        // Monster 1 moves
        assertEquals(monster0.getMoves().get(0).getName(), movesToTest1.get(0).getName());
        assertEquals(monster0.getMoves().get(0).getDamage(), movesToTest1.get(0).getDamage());
        assertEquals(monster0.getMoves().get(0).getType(), movesToTest1.get(0).getType());
        assertEquals(monster0.getMoves().get(1).getName(), movesToTest1.get(1).getName());
        assertEquals(monster0.getMoves().get(1).getDamage(), movesToTest1.get(1).getDamage());
        assertEquals(monster0.getMoves().get(1).getType(), movesToTest1.get(1).getType());

        // Monster 2
        assertEquals(monster1.getName(), monsterToTest2.getName());
        assertEquals(monster1.getMonsterType(), monsterToTest2.getMonsterType());
        assertEquals(monster1.getDefence(), monsterToTest2.getDefence());
        assertEquals(monster1.getAttack(), monsterToTest2.getAttack());
        assertEquals(monster1.getSpeed(), monsterToTest2.getSpeed());
        assertEquals(monster1.getFrontSprite(), monsterToTest2.getFrontSprite());
        assertEquals(monster1.getBackSprite(), monsterToTest2.getBackSprite());
        assertEquals(monster1.getID(), monsterToTest2.getID());
        // Monster 2 moves
        assertEquals(monster1.getMoves().get(0).getName(), movesToTest2.get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), movesToTest2.get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), movesToTest2.get(0).getType());

        // Monsters get cloned and are not the same
        assertNotEquals(registry.getMonster(0), registry.getMonster(0));
        assertNotEquals(registry.getMonster(1), registry.getMonster(1));
    }

    @Test
    @Order(3)
    void monsterRegistry_ClonedNotCopied_isTrue() {
        // Monsters get cloned and are not the same
        assertNotEquals(registry.getMonster(0), registry.getMonster(0));
        assertNotEquals(registry.getMonster(1), registry.getMonster(1));
    }

    @Test
    @Order(4)
    void monsterRegistry_getArray_isAccurate() {
        IMonster[] monstersToTest = registry.getAllMonsters();
        Monster monsterToTest0 = (Monster) monstersToTest[0];
        Monster monsterToTest1 = (Monster) monstersToTest[1];
        List<IMonsterMove> movesToTest1 = monsterToTest0.getMoves();
        List<IMonsterMove> movesToTest2 = monsterToTest1.getMoves();

        //// Assert
        // Monster 1
        assertEquals(monster0.getName(), monsterToTest0.getName());
        assertEquals(monster0.getMonsterType(), monsterToTest0.getMonsterType());
        assertEquals(monster0.getDefence(), monsterToTest0.getDefence());
        assertEquals(monster0.getAttack(), monsterToTest0.getAttack());
        assertEquals(monster0.getSpeed(), monsterToTest0.getSpeed());
        assertEquals(monster0.getFrontSprite(), monsterToTest0.getFrontSprite());
        assertEquals(monster0.getBackSprite(), monsterToTest0.getBackSprite());
        assertEquals(monster0.getID(), monsterToTest0.getID());

        // Monster 1 moves
        assertEquals(monster0.getMoves().get(0).getName(), movesToTest1.get(0).getName());
        assertEquals(monster0.getMoves().get(0).getDamage(), movesToTest1.get(0).getDamage());
        assertEquals(monster0.getMoves().get(0).getType(), movesToTest1.get(0).getType());
        assertEquals(monster0.getMoves().get(1).getName(), movesToTest1.get(1).getName());
        assertEquals(monster0.getMoves().get(1).getDamage(), movesToTest1.get(1).getDamage());
        assertEquals(monster0.getMoves().get(1).getType(), movesToTest1.get(1).getType());

        // Monster 2
        assertEquals(monster1.getName(), monsterToTest1.getName());
        assertEquals(monster1.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(monster1.getDefence(), monsterToTest1.getDefence());
        assertEquals(monster1.getAttack(), monsterToTest1.getAttack());
        assertEquals(monster1.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(monster1.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(monster1.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(monster1.getID(), monsterToTest1.getID());
        // Monster 2 moves
        assertEquals(monster1.getMoves().get(0).getName(), movesToTest2.get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), movesToTest2.get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), movesToTest2.get(0).getType());
    }
}
