import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void monster_health_should_be_reduced(){
        // Arrange
        MonsterPlugin monsterPlugin = new MonsterPlugin();
        IMonster monster = monsterPlugin.getPigMonster();
        int newHP = 25;

        // Act
        monster.setHitPoints(newHP);

        // Assert
        assertEquals(monster.getHitPoints(), newHP, "Hitpoints should be reduced by " + newHP);
    }

    @Test
    void monster_getName_returns_correct_name(){
        // Arrange
        MonsterPlugin monsterPlugin = new MonsterPlugin();
        String nameToCheck = "Hippopotamus";
        IMonster monster = new Monster(nameToCheck, MonsterType.EARTH, 1, 1, 1, 1, new ArrayList<>());
        Monster monster2 = new Monster(nameToCheck, MonsterType.EARTH, 1, 1, 1, 1, new ArrayList<>());

        // Act
        String newNameToCheck = "Giraffe";
        monster2.setName(newNameToCheck);

        // Assert
        assertEquals(monster.getName(), nameToCheck, "Name should be " + nameToCheck);
        assertEquals(monster2.getName(), newNameToCheck, "Name should be " + newNameToCheck);
    }
}