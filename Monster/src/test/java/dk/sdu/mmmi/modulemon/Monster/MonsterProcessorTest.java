package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class MonsterProcessorTest {


    @BeforeAll
    static void setupMonsters() {

    }


    @Test
    void fasterMonsterShouldStart() {
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        IMonster slowMonster = new Monster("Monster", MonsterType.WATER, 100, 10, 10, 50,
                Arrays.asList());
        IMonster fastMonster = new Monster("Monster", MonsterType.WATER, 100, 10, 10, 100,
                Arrays.asList());
        assertEquals(fastMonster, processor.whichMonsterStarts(slowMonster, fastMonster));
    }

    @Test // Attacking monster with half defence of your attack should do double damage
    void monsterProcessor_attackVSdefence_correctlyCalculated() {
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        int damage = 10;
        IMonster strongMonster = new Monster("Monster", MonsterType.AIR, 100, 20, 20, 10,
                Arrays.asList());
        IMonster weakMonster = new Monster("Monster", MonsterType.AIR, 100, 10, 10, 10,
                Arrays.asList());
        assertEquals(damage * 2, processor.calculateDamage(strongMonster, new MonsterMove("Basic", damage, MonsterType.AIR), weakMonster));
        assertEquals(damage / 2, processor.calculateDamage(weakMonster, new MonsterMove("Basic", damage, MonsterType.AIR), strongMonster));
    }

    @Test
    void monsterProcessor_typeDamage_correctlyCalculated() {
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        int damage = 10;
        IMonster fireMonster = new Monster("Monster", MonsterType.FIRE, 100, 10, 10, 10,
                Arrays.asList());
        IMonster waterMonster = new Monster("Monster", MonsterType.WATER, 100, 10, 10, 10,
                Arrays.asList());
        IMonster earthMonster = new Monster("Monster", MonsterType.EARTH, 100, 10, 10, 10,
                Arrays.asList());
        assertEquals(processor.calculateDamage(fireMonster, new MonsterMove("fire", damage, MonsterType.FIRE), waterMonster), damage / 2);
        assertEquals(damage * 2, processor.calculateDamage(waterMonster, new MonsterMove("water", damage, MonsterType.WATER), fireMonster));
        assertEquals(damage * 0, processor.calculateDamage(waterMonster, new MonsterMove("lightning", damage, MonsterType.LIGHTNING), earthMonster));

    }
}
