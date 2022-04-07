package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MonsterParserTest {
    static IMonster[] monsters;
    static IMonster monster0;
    static IMonster monster1;
    static IMonster[] monstersToTest;

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

        monster0 = monsters[0];
        monster1 = monsters[1];

        monstersToTest = MonsterParser.parseMonsters(
                "/json/monsters_test.json",
                "/json/monsters_moves_test.json"
        );
    }

    @Test
    @Order(1)
    void monsterParser_parseMonsters_notNull() {
        assertNotNull(monstersToTest);
        assertEquals(2, monstersToTest.length);
    }

    @Test
    @Order(2)
    void monsterParser_parseMonster_isAccurate() {
        IMonster monsterToTest0 = monstersToTest[0];
        IMonster monsterToTest1 = monstersToTest[1];

        // Monster 1
        assertEquals(monster0.getName(), monsterToTest0.getName());
        assertEquals(monster0.getMonsterType(), monsterToTest0.getMonsterType());
        assertEquals(monster0.getDefence(), monsterToTest0.getDefence());
        assertEquals(monster0.getAttack(), monsterToTest0.getAttack());
        assertEquals(monster0.getSpeed(), monsterToTest0.getSpeed());
        assertEquals(monster0.getFrontSprite(), monsterToTest0.getFrontSprite());
        assertEquals(monster0.getBackSprite(), monsterToTest0.getBackSprite());
        assertEquals(monster0.getID(), monsterToTest0.getID());

        // Monster 2
        assertEquals(monster1.getName(), monsterToTest1.getName());
        assertEquals(monster1.getMonsterType(), monsterToTest1.getMonsterType());
        assertEquals(monster1.getDefence(), monsterToTest1.getDefence());
        assertEquals(monster1.getAttack(), monsterToTest1.getAttack());
        assertEquals(monster1.getSpeed(), monsterToTest1.getSpeed());
        assertEquals(monster1.getFrontSprite(), monsterToTest1.getFrontSprite());
        assertEquals(monster1.getBackSprite(), monsterToTest1.getBackSprite());
        assertEquals(monster1.getID(), monsterToTest1.getID());

    }

    @Test
    @Order(3)
    void monsterParser_parseMonsterMoves_isAccurate() {
        List<IMonsterMove> movesToTest0 = monstersToTest[0].getMoves();
        List<IMonsterMove> movesToTest1 = monstersToTest[1].getMoves();

        // Monster 1 moves
        assertEquals(monster0.getMoves().get(0).getName(), movesToTest0.get(0).getName());
        assertEquals(monster0.getMoves().get(0).getDamage(), movesToTest0.get(0).getDamage());
        assertEquals(monster0.getMoves().get(0).getType(), movesToTest0.get(0).getType());
        assertEquals(monster0.getMoves().get(1).getName(), movesToTest0.get(1).getName());
        assertEquals(monster0.getMoves().get(1).getDamage(), movesToTest0.get(1).getDamage());
        assertEquals(monster0.getMoves().get(1).getType(), movesToTest0.get(1).getType());

        // Monster 2 moves
        assertEquals(monster1.getMoves().get(0).getName(), movesToTest1.get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), movesToTest1.get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), movesToTest1.get(0).getType());
    }

    @Test
    @Order(4)
    void monsterParser_validButWrongURL_failsCorrectly() {
        assertThrows(IOException.class, () -> MonsterParser.parseMonsters(
                "/json",
                "/json"
        ));
    }

    @Test
    @Order(5)
    void monsterParser_invalidURL_failsCorrectly() {
        assertThrows(NullPointerException.class, () -> MonsterParser.parseMonsters(
                "/json/wrong.json",
                "/json/wrong.json"
        ));
    }
}