import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.Monster.Monster;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonsterTest {

    @Test
    void monster_health_should_be_reduced(){
        // Arrange
        int newHP = 25;
        IMonster monster = new Monster("Hippopotamus", MonsterType.EARTH, 50, 1, 1, 1, new ArrayList<>());

        // Act
        monster.setHitPoints(newHP);

        // Assert
        assertEquals(monster.getHitPoints(), newHP, "Hitpoints should be reduced to " + newHP);
    }

    @Test
    void monster_getName_returns_correct_name(){
        // Arrange
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
