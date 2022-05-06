package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

public class MonsterProcessorTest {


    @BeforeAll
    static void setupMonsters() {

    }

    @Test
    void fasterMonsterShouldStart() {
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        IMonster slowMonster = new Monster("Monster", MonsterType.WATER, 100, 10, 10, 50,
                Collections.emptyList());
        IMonster fastMonster = new Monster("Monster", MonsterType.WATER, 100, 10, 10, 100,
                Collections.emptyList());
        assertEquals(fastMonster, processor.whichMonsterStarts(slowMonster, fastMonster));
    }

    @Test // Moves should do double damage against monsters weak to the move type. And 0 to types immune to the move type
    void monsterProcessor_typeAdvantage_correctlyCalculated() {
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        int damage = 10;
        int defence = 10;
        int attack = 10;
        IMonster fireMonster = new Monster("Monster", MonsterType.FIRE, 100, defence, attack, 10,
                Collections.emptyList());
        IMonster airMonster = new Monster("Monster", MonsterType.AIR, 100, defence, attack, 10,
                Collections.emptyList());
        IMonster earthMonster = new Monster("Monster", MonsterType.EARTH, 100, defence, attack, 10,
                Collections.emptyList());


        int moveDamage = Math.round( ( (0.2f * attack + 3 + 20 ) / (defence + 50) ) * damage);
        IMonsterMove waterMove = new MonsterMove("water", damage, MonsterType.WATER);
        IMonsterMove lightningMove = new MonsterMove("lightning", damage, MonsterType.LIGHTNING);


        assertEquals(processor.calculateDamage(fireMonster, new MonsterMove("fire", damage, MonsterType.GRASS), airMonster), moveDamage / 2);
        assertEquals(moveDamage * 2f, processor.calculateDamage(airMonster, waterMove, fireMonster));
        assertEquals(moveDamage * 0f, processor.calculateDamage(airMonster, lightningMove, earthMonster));
    }

    @Test // Attacking monster should get a bonus if it has the same type as the move it is using.
    void monsterProcessor_monsterSameAttackType_correctlyCalculated(){
        BattleMonsterProcessor processor = new BattleMonsterProcessor();
        int damage = 10;
        int defence = 10;
        int attack = 10;


        IMonster fireMonster = new Monster("Monster", MonsterType.FIRE, 100, defence, attack, 10,
                Collections.emptyList());
        IMonster earthMonster = new Monster("Monster", MonsterType.EARTH, 100, defence, attack, 10,
                Collections.emptyList());

        int moveDamage = Math.round( ( (0.2f * attack + 3 + 20 ) / (defence + 50) ) * damage);
        IMonsterMove fireMove = new MonsterMove("fire", damage, MonsterType.FIRE);
        IMonsterMove earthMove = new MonsterMove("earth", damage, MonsterType.EARTH);

        assertEquals(moveDamage * 1.5f, processor.calculateDamage(fireMonster, fireMove, earthMonster));
        assertEquals(moveDamage * 1f, processor.calculateDamage(fireMonster, earthMove, earthMonster));
    }


}
