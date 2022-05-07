package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.json.JSONException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterParserTest {
    static IMonster[] expectedMonsters;
    static Monster expectedMonster0;
    static Monster expectedMonster1;
    static List<MonsterMove> expectedMoves0;
    static List<MonsterMove> expectedMoves1;
    static IMonster[] actualMonsters;


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

        actualMonsters = MonsterParser.parseMonsters(
                "/json/monsters_test.json",
                "/json/monsters_moves_test.json"
        );
    }

    @Test
    @Order(1)
    void monsterParser_parseMonsters_notNull() {
        assertNotNull(actualMonsters);
        assertEquals(2, actualMonsters.length);
    }

    @Test
    @Order(2)
    void monsterParser_parseMonster_isAccurate() {
        Monster monsterToTest0 = (Monster) actualMonsters[0];
        Monster monsterToTest1 = (Monster) actualMonsters[1];

        // Monster 0
        assertEquals(expectedMonster0.getName(), monsterToTest0.getName());
        assertEquals(expectedMonster0.getMonsterType(), monsterToTest0.getMonsterType());
        assertEquals(expectedMonster0.getDefence(), monsterToTest0.getDefence());
        assertEquals(expectedMonster0.getAttack(), monsterToTest0.getAttack());
        assertEquals(expectedMonster0.getSpeed(), monsterToTest0.getSpeed());
        assertEquals(expectedMonster0.getFrontSprite(), monsterToTest0.getFrontSprite());
        assertEquals(expectedMonster0.getBackSprite(), monsterToTest0.getBackSprite());
        assertEquals(expectedMonster0.getID(), monsterToTest0.getID());

        // Monster 1
        assertEquals(expectedMonster1.getName(), monsterToTest1.getName());
        assertEquals(expectedMonster1.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(expectedMonster1.getDefence(), monsterToTest1.getDefence());
        assertEquals(expectedMonster1.getAttack(), monsterToTest1.getAttack());
        assertEquals(expectedMonster1.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(expectedMonster1.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(expectedMonster1.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(expectedMonster1.getID(), monsterToTest1.getID());

    }

    @Test
    @Order(3)
    void monsterParser_parseMonsterMoves_isAccurate() {
        List<MonsterMove> actualMoves0 = new ArrayList<>();
        for (IMonsterMove move : actualMonsters[0].getMoves()) {
            actualMoves0.add((MonsterMove) move);
        }
        List<MonsterMove> actualMoves1 = new ArrayList<>();
        for (IMonsterMove move : actualMonsters[1].getMoves()) {
            actualMoves1.add((MonsterMove) move);
        }

        // Monster 0 moves
        assertEquals(expectedMoves0.get(0).getName(), actualMoves0.get(0).getName());
        assertEquals(expectedMoves0.get(0).getDamage(), actualMoves0.get(0).getDamage());
        assertEquals(expectedMoves0.get(0).getType(), actualMoves0.get(0).getType());
        assertEquals(expectedMoves0.get(1).getName(), actualMoves0.get(1).getName());
        assertEquals(expectedMoves0.get(1).getDamage(), actualMoves0.get(1).getDamage());
        assertEquals(expectedMoves0.get(1).getType(), actualMoves0.get(1).getType());

        // Monster 1 moves
        assertEquals(expectedMoves1.get(0).getName(), actualMoves1.get(0).getName());
        assertEquals(expectedMoves1.get(0).getDamage(), actualMoves1.get(0).getDamage());
        assertEquals(expectedMoves1.get(0).getType(), actualMoves1.get(0).getType());
        assertEquals(expectedMoves1.get(0).getSoundPath(), actualMoves1.get(0).getSoundPath());
    }

    @Test
    @Order(4)
    void monsterParser_validButWrongURL_failsCorrectly() {
        assertThrows(JSONException.class, () -> MonsterParser.parseMonsters(
                "/json",
                "/json"
        ));
    }

    @Test
    @Order(5)
    void monsterParser_invalidURL_failsCorrectly() {
        assertThrows(JSONException.class, () -> MonsterParser.parseMonsters(
                "/json/wrong.json",
                "/json/wrong.json"
        ));
    }
}