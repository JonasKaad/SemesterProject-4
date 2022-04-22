package dk.sdu.mmmi.modulemon.BattleSceneMock;

import dk.sdu.mmmi.modulemon.Battle.BattleParticipant;
import dk.sdu.mmmi.modulemon.Monster.Monster;
import dk.sdu.mmmi.modulemon.Monster.MonsterMove;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.ArrayList;
import java.util.List;

/**
 * A static helper class that just instansiates some Battle participants with some monsters, while we don't dynimically create those
 */
public class BattleParticipantMocks {

    public static IBattleParticipant getPlayer() {
        List<IMonster> monsters = new ArrayList<>();
        List<IMonsterMove> eelMoves = new ArrayList<>();
        List<IMonsterMove> alpacaMoves = new ArrayList<>();
        List<IMonsterMove> bulbasaurMoves = new ArrayList<>();
        List<IMonsterMove> charmanderMoves = new ArrayList<>();

        eelMoves.add(new MonsterMove("Tackle", 5, MonsterType.AIR));
        eelMoves.add(new MonsterMove("Zap", 10, MonsterType.LIGHTNING));
        eelMoves.add(new MonsterMove("Egg Bomb", 20, MonsterType.GRASS));
        eelMoves.add(new MonsterMove("Water Blast", 30, MonsterType.WATER));
        monsters.add(new Monster(
                "Eel",
                MonsterType.WATER,
                100,
                10,
                20,
                5,
                eelMoves,
                "/monsters/eelF.png",
                "/monsters/eelB.png",
                1
        ));


        alpacaMoves.add(new MonsterMove("Spit", 10, MonsterType.WATER));
        alpacaMoves.add(new MonsterMove("Trample", 15, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Horn Blast", 25, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Fire Breath", 50, MonsterType.FIRE));
        monsters.add(new Monster(
                "Alpaca",
                MonsterType.EARTH,
                100,
                10,
                20,
                5,
                alpacaMoves,
                "/monsters/alpacaF.png",
                "/monsters/alpacaB.png",
                2
        ));

        /*
        bulbasaurMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        bulbasaurMoves.add(new MockMonsterMove("Vine Whip", MonsterType.GRASS, 10));
        bulbasaurMoves.add(new MockMonsterMove("Razor Leaf", MonsterType.GRASS, 30));
        monsters.add(new MockMonster(
                "Bulbasaur",
                30,
                10,
                20,
                5,
                "/monsters/001.png",
                "/monsters/001b.png",
                MonsterType.GRASS,
                bulbasaurMoves
        ));

        charmanderMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        charmanderMoves.add(new MockMonsterMove("Ember", MonsterType.FIRE, 15));
        charmanderMoves.add(new MockMonsterMove("Fire Blast", MonsterType.FIRE, 25));
        charmanderMoves.add(new MockMonsterMove("Flamethrower", MonsterType.FIRE, 50));
        monsters.add(new MockMonster(
                "Charmander",
                40,
                10,
                20,
                5,
                "/monsters/004.png",
                "/monsters/004b.png",
                MonsterType.FIRE,
                charmanderMoves
        ));*/

        IBattleParticipant player = new BattleParticipant(monsters, true);
        return player;
    }

    public static IBattleParticipant getOpponent() {
        List<IMonster> monsters = new ArrayList<>();
        List<IMonsterMove> eelMoves = new ArrayList<>();
        List<IMonsterMove> alpacaMoves = new ArrayList<>();
        List<IMonsterMove> bulbasaurMoves = new ArrayList<>();
        List<IMonsterMove> charmanderMoves = new ArrayList<>();

        eelMoves.add(new MonsterMove("Tackle", 5, MonsterType.AIR));
        eelMoves.add(new MonsterMove("Zap", 10, MonsterType.LIGHTNING));
        eelMoves.add(new MonsterMove("Egg Bomb", 20, MonsterType.GRASS));
        eelMoves.add(new MonsterMove("Water Blast", 30, MonsterType.WATER));
        monsters.add(new Monster(
                "Eel",
                MonsterType.WATER,
                100,
                10,
                20,
                5,
                eelMoves,
                "/monsters/eelF.png",
                "/monsters/eelB.png",
                1
        ));

        alpacaMoves.add(new MonsterMove("Spit", 10, MonsterType.WATER));
        alpacaMoves.add(new MonsterMove("Trample", 15, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Horn Blast", 25, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Fire Breath", 300, MonsterType.FIRE));
        monsters.add(new Monster(
                "Alpaca",
                MonsterType.EARTH,
                100,
                10,
                20,
                5,
                alpacaMoves,
                "/monsters/alpacaF.png",
                "/monsters/alpacaB.png",
                2
        ));

        /*
        slowpokeMoves.add(new MockMonsterMove("Water gun", MonsterType.WATER, 15));
        slowpokeMoves.add(new MockMonsterMove("Confusion", MonsterType.LIGHTNING, 30));
        monsters.add(new MockMonster(
                "Slowpoke",
                10,
                10,
                20,
                5,
                "/monsters/079.png",
                "/monsters/079b.png",
                MonsterType.WATER,
                slowpokeMoves
        ));

        squrtleMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        squrtleMoves.add(new MockMonsterMove("Water gun", MonsterType.WATER, 10));
        squrtleMoves.add(new MockMonsterMove("Surf", MonsterType.WATER, 25));
        monsters.add(new MockMonster(
                "Squrtle",
                40,
                10,
                20,
                5,
                "/monsters/007.png",
                "/monsters/007b.png",
                MonsterType.FIRE,
                squrtleMoves
        ));
         */

        IBattleParticipant enemy = new BattleParticipant(monsters, false);
        return enemy;
    }

}
