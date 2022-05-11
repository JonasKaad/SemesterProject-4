package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterRegistryTest {
    static IMonster[] expectedMonsters;
    static Monster expectedMonster0;
    static Monster expectedMonster1;
    static IMonsterRegistry registry;
    static List<MonsterMove> expectedMoves0;
    static List<MonsterMove> expectedMoves1;

    @BeforeAll
    static void setupMonsters() throws IOException, URISyntaxException {
        expectedMonsters = new IMonster[2];
        expectedMonsters[0] = new Monster("Alpaca",
                MonsterType.GRASS,
                100,
                25,
                60,
                60,
                Arrays.asList(
                        new MonsterMove("Spit", 10, MonsterType.WATER),
                        new MonsterMove("Trample", 25, MonsterType.GRASS)),
                "/images/alpaca_1.png",
                "/images/alpaca_2.png",
                0);
        expectedMonsters[1] = new Monster("Eel",
                MonsterType.WATER,
                90,
                20,
                70,
                70,
                Arrays.asList(new MonsterMove("Zap", 20, MonsterType.LIGHTNING, "/sounds/zap.ogg")),
                "/images/eel_1.png",
                "/images/eel_2.png",
                1);
        expectedMonster0 = (Monster) expectedMonsters[0];
        expectedMonster1 = (Monster) expectedMonsters[1];

        expectedMoves0 = new ArrayList<>();
        expectedMoves1 = new ArrayList<>();
        for (IMonsterMove move : expectedMonster0.getMoves()) {
            expectedMoves0.add((MonsterMove) move);
        }
        for (IMonsterMove move : expectedMonster1.getMoves()) {
            expectedMoves1.add((MonsterMove) move);
        }

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
        Monster actualMonster0 = (Monster) registry.getMonster(0);
        Monster actualMonster1 = (Monster) registry.getMonster(1);

        List<MonsterMove> actualMoves0 = new ArrayList<>();
        for (IMonsterMove move : actualMonster0.getMoves()) {
            actualMoves0.add((MonsterMove) move);
        }
        List<MonsterMove> actualMoves1 = new ArrayList<>();
        for (IMonsterMove move : actualMonster1.getMoves()) {
            actualMoves1.add((MonsterMove) move);
        }

        //// Assert
        // Monster 0
        assertEquals(expectedMonster0.getName(), actualMonster0.getName());
        assertEquals(expectedMonster0.getMonsterType(), actualMonster0.getMonsterType());
        assertEquals(expectedMonster0.getDefence(), actualMonster0.getDefence());
        assertEquals(expectedMonster0.getAttack(), actualMonster0.getAttack());
        assertEquals(expectedMonster0.getSpeed(), actualMonster0.getSpeed());
        assertEquals(expectedMonster0.getFrontSprite(), actualMonster0.getFrontSprite());
        assertEquals(expectedMonster0.getBackSprite(), actualMonster0.getBackSprite());
        assertEquals(expectedMonster0.getID(), actualMonster0.getID());

        // Monster 0 moves
        assertEquals(expectedMoves0.get(0).getName(), actualMoves0.get(0).getName());
        assertEquals(expectedMoves0.get(0).getDamage(), actualMoves0.get(0).getDamage());
        assertEquals(expectedMoves0.get(0).getType(), actualMoves0.get(0).getType());
        assertEquals(expectedMoves0.get(1).getName(), actualMoves0.get(1).getName());
        assertEquals(expectedMoves0.get(1).getDamage(), actualMoves0.get(1).getDamage());
        assertEquals(expectedMoves0.get(1).getType(), actualMoves0.get(1).getType());

        // Monster 1
        assertEquals(expectedMonster1.getName(), actualMonster1.getName());
        assertEquals(expectedMonster1.getMonsterType(), actualMonster1.getMonsterType());
        assertEquals(expectedMonster1.getDefence(), actualMonster1.getDefence());
        assertEquals(expectedMonster1.getAttack(), actualMonster1.getAttack());
        assertEquals(expectedMonster1.getSpeed(), actualMonster1.getSpeed());
        assertEquals(expectedMonster1.getFrontSprite(), actualMonster1.getFrontSprite());
        assertEquals(expectedMonster1.getBackSprite(), actualMonster1.getBackSprite());
        assertEquals(expectedMonster1.getID(), actualMonster1.getID());
        // Monster 1 moves
        assertEquals(expectedMoves1.get(0).getName(), actualMoves1.get(0).getName());
        assertEquals(expectedMoves1.get(0).getDamage(), actualMoves1.get(0).getDamage());
        assertEquals(expectedMoves1.get(0).getType(), actualMoves1.get(0).getType());
        assertEquals(expectedMoves1.get(0).getSoundPath(), actualMoves1.get(0).getSoundPath());

        // Monsters get cloned and are not the same
        assertNotSame(registry.getMonster(0), registry.getMonster(0));
        assertNotSame(registry.getMonster(1), registry.getMonster(1));
    }

    @Test
    @Order(3)
    void monsterRegistry_ClonedNotCopied_isTrue() {
        // Monsters get cloned and are not the same
        assertNotSame(registry.getMonster(0), registry.getMonster(0));
        assertNotSame(registry.getMonster(1), registry.getMonster(1));
    }

    @Test
    @Order(4)
    void monsterRegistry_getArray_isAccurate() {
        IMonster[] actualMonsters = registry.getAllMonsters();
        Monster actualMonster0 = (Monster) actualMonsters[0];
        Monster actualMonster1 = (Monster) actualMonsters[1];

        List<MonsterMove> actualMoves0 = new ArrayList<>();
        for (IMonsterMove move : actualMonster0.getMoves()) {
            actualMoves0.add((MonsterMove) move);
        }
        List<MonsterMove> actualMoves1 = new ArrayList<>();
        for (IMonsterMove move : actualMonster1.getMoves()) {
            actualMoves1.add((MonsterMove) move);
        }

        //// Assert
        // Monster 0
        assertEquals(expectedMonster0.getName(), actualMonster0.getName());
        assertEquals(expectedMonster0.getMonsterType(), actualMonster0.getMonsterType());
        assertEquals(expectedMonster0.getDefence(), actualMonster0.getDefence());
        assertEquals(expectedMonster0.getAttack(), actualMonster0.getAttack());
        assertEquals(expectedMonster0.getSpeed(), actualMonster0.getSpeed());
        assertEquals(expectedMonster0.getFrontSprite(), actualMonster0.getFrontSprite());
        assertEquals(expectedMonster0.getBackSprite(), actualMonster0.getBackSprite());
        assertEquals(expectedMonster0.getID(), actualMonster0.getID());

        // Monster 0 moves
        assertEquals(expectedMoves0.get(0).getName(), actualMoves0.get(0).getName());
        assertEquals(expectedMoves0.get(0).getDamage(), actualMoves0.get(0).getDamage());
        assertEquals(expectedMoves0.get(0).getType(), actualMoves0.get(0).getType());
        assertEquals(expectedMoves0.get(1).getName(), actualMoves0.get(1).getName());
        assertEquals(expectedMoves0.get(1).getDamage(), actualMoves0.get(1).getDamage());
        assertEquals(expectedMoves0.get(1).getType(), actualMoves0.get(1).getType());

        // Monster 1
        assertEquals(expectedMonster1.getName(), actualMonster1.getName());
        assertEquals(expectedMonster1.getMonsterType(), actualMonster1.getMonsterType());
        assertEquals(expectedMonster1.getDefence(), actualMonster1.getDefence());
        assertEquals(expectedMonster1.getAttack(), actualMonster1.getAttack());
        assertEquals(expectedMonster1.getSpeed(), actualMonster1.getSpeed());
        assertEquals(expectedMonster1.getFrontSprite(), actualMonster1.getFrontSprite());
        assertEquals(expectedMonster1.getBackSprite(), actualMonster1.getBackSprite());
        assertEquals(expectedMonster1.getID(), actualMonster1.getID());
        // Monster 1 moves
        assertEquals(expectedMoves1.get(0).getName(), actualMoves1.get(0).getName());
        assertEquals(expectedMoves1.get(0).getDamage(), actualMoves1.get(0).getDamage());
        assertEquals(expectedMoves1.get(0).getType(), actualMoves1.get(0).getType());
        assertEquals(expectedMoves1.get(0).getSoundPath(), actualMoves1.get(0).getSoundPath());
    }
}
