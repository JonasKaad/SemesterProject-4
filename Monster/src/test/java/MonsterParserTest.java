import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.Monster.Monster;
import dk.sdu.mmmi.modulemon.Monster.MonsterMove;
import dk.sdu.mmmi.modulemon.Monster.MonsterParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonsterParserTest {
    @Test
    void monsterParser_parseMonster_isAccurate() throws IOException, URISyntaxException {
        // Arrange
        MonsterMove moveOne = new MonsterMove("Spit", 10, MonsterType.WATER);
        MonsterMove moveTwo = new MonsterMove("Trample", 25, MonsterType.GRASS);
        MonsterMove moveThree = new MonsterMove("Zap", 20, MonsterType.LIGHTNING);
        IMonster monster1 = new Monster("Alpaca", MonsterType.GRASS, 100, 25, 60, 60, Arrays.asList(moveOne, moveTwo));
        IMonster monster2 = new Monster("Eel", MonsterType.WATER, 90, 20, 70, 70, Arrays.asList(moveThree));

        // Act
        List<IMonster> monsters = MonsterParser.parseMonsters(
                    "json/monsters_test.json",
                    "json/monsters_moves_test.json"
            );


        //// Assert
        // Monster 1
        assertEquals(monster1.getName(), monsters.get(0).getName());
        assertEquals(monster1.getMonsterType(), monsters.get(0).getMonsterType());
        assertEquals(monster1.getDefence(), monsters.get(0).getDefence());
        assertEquals(monster1.getAttack(), monsters.get(0).getAttack());
        assertEquals(monster1.getSpeed(), monsters.get(0).getSpeed());
        // Monster 1 moves
        assertEquals(monster1.getMoves().get(0).getName(), monsters.get(0).getMoves().get(0).getName());
        assertEquals(monster1.getMoves().get(0).getDamage(), monsters.get(0).getMoves().get(0).getDamage());
        assertEquals(monster1.getMoves().get(0).getType(), monsters.get(0).getMoves().get(0).getType());
        assertEquals(monster1.getMoves().get(1).getName(), monsters.get(0).getMoves().get(1).getName());
        assertEquals(monster1.getMoves().get(1).getDamage(), monsters.get(0).getMoves().get(1).getDamage());
        assertEquals(monster1.getMoves().get(1).getType(), monsters.get(0).getMoves().get(1).getType());

        // Monster 2
        assertEquals(monster2.getName(), monsters.get(1).getName());
        assertEquals(monster2.getMonsterType(), monsters.get(1).getMonsterType());
        assertEquals(monster2.getDefence(), monsters.get(1).getDefence());
        assertEquals(monster2.getAttack(), monsters.get(1).getAttack());
        assertEquals(monster2.getSpeed(), monsters.get(1).getSpeed());
        // Monster 2 moves
        assertEquals(monster2.getMoves().get(0).getName(), monsters.get(1).getMoves().get(0).getName());
        assertEquals(monster2.getMoves().get(0).getDamage(), monsters.get(1).getMoves().get(0).getDamage());
        assertEquals(monster2.getMoves().get(0).getType(), monsters.get(1).getMoves().get(0).getType());
    }
}