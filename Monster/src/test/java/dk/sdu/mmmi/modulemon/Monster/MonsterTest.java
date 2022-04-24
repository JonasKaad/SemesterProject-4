package dk.sdu.mmmi.modulemon.Monster;

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

    @Test
    void monster_clone_returns_new_object() {
        Monster monster = new Monster("MonsterName", MonsterType.EARTH, 1, 1, 1, 1, new ArrayList<>());
        Monster clone = (Monster) monster.clone();
        assertNotSame(monster, clone);
    }

    @Test
    void monster_clone_has_same_properties() {
        String name = "MonsterName";
        MonsterType type = MonsterType.EARTH;
        int hitPoints = 1;
        int defence = 2;
        int attack = 3;
        int speed = 4;
        ArrayList<IMonsterMove> moves = new ArrayList<>();
        Monster monster = new Monster(name, type, hitPoints, defence, attack, speed, moves);
        Monster clone = (Monster) monster.clone();
        assertEquals(name, clone.getName());
        assertEquals(type, clone.getMonsterType());
        assertEquals(hitPoints, clone.getHitPoints());
        assertEquals(defence, clone.getDefence());
        assertEquals(attack, clone.getAttack());
        assertEquals(speed, clone.getSpeed());
        assertEquals(moves, clone.getMoves());
    }
}
