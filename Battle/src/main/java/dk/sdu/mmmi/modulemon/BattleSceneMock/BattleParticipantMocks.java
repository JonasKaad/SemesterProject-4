package dk.sdu.mmmi.modulemon.BattleSceneMock;

import dk.sdu.mmmi.modulemon.Battle.BattleParticipant;
import dk.sdu.mmmi.modulemon.Monster.Monster;
import dk.sdu.mmmi.modulemon.Monster.MonsterMove;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.Monster.MonsterRegistry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * A static helper class that just instansiates some Battle participants with some monsters, while we don't dynimically create those
 */
public class BattleParticipantMocks {

    public static IBattleParticipant getPlayer() throws IOException, URISyntaxException {
        IMonsterRegistry monsterRegistry = new MonsterRegistry();
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(monsterRegistry.getMonster(0));
        monsters.add(monsterRegistry.getMonster(1));

        /*
        List<IMonster> monsters = new ArrayList<>();
        List<IMonsterMove> eelMoves = new ArrayList<>();
        List<IMonsterMove> alpacaMoves = new ArrayList<>();
        List<IMonsterMove> bulbasaurMoves = new ArrayList<>();
        List<IMonsterMove> charmanderMoves = new ArrayList<>();

<<<<<<< HEAD
        eelMoves.add(new MonsterMove("Tackle", 5, MonsterType.AIR));
        eelMoves.add(new MonsterMove("Zap", 10, MonsterType.LIGHTNING));
        eelMoves.add(new MonsterMove("Egg Bomb", 20, MonsterType.GRASS));
        eelMoves.add(new MonsterMove("Water Blast", 30, MonsterType.WATER));
        monsters.add(new Monster(
=======
        eelMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        eelMoves.add(new MockMonsterMove("Zap", MonsterType.LIGHTNING, 10, "/sounds/Zap.ogg"));
        eelMoves.add(new MockMonsterMove("Egg Bomb", MonsterType.GRASS, 20, "/sounds/Egg Bomb.ogg"));
        eelMoves.add(new MockMonsterMove("Water Blast", MonsterType.WATER, 30, "/sounds/Water Blast.ogg"));
        monsters.add(new MockMonster(
>>>>>>> master
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


<<<<<<< HEAD
        alpacaMoves.add(new MonsterMove("Spit", 10, MonsterType.WATER));
        alpacaMoves.add(new MonsterMove("Trample", 15, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Horn Blast", 25, MonsterType.EARTH));
        alpacaMoves.add(new MonsterMove("Fire Breath", 50, MonsterType.FIRE));
        monsters.add(new Monster(
=======
        alpacaMoves.add(new MockMonsterMove("Spit", MonsterType.WATER, 10, "/sounds/Spit.ogg"));
        alpacaMoves.add(new MockMonsterMove("Trample", MonsterType.EARTH, 15, "/sounds/Trample.ogg"));
        alpacaMoves.add(new MockMonsterMove("Horn Blast", MonsterType.EARTH, 25, "/sounds/Horn Blast.ogg"));
        alpacaMoves.add(new MockMonsterMove("Fire Breath", MonsterType.FIRE, 50, "/sounds/Fire Breath.ogg"));
        monsters.add(new MockMonster(
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

    public static IBattleParticipant getOpponent() throws IOException, URISyntaxException {
        IMonsterRegistry monsterRegistry = new MonsterRegistry();
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(monsterRegistry.getMonster(2));
        monsters.add(monsterRegistry.getMonster(3));

        /*
        List<IMonster> monsters = new ArrayList<>();
        List<IMonsterMove> eelMoves = new ArrayList<>();
        List<IMonsterMove> alpacaMoves = new ArrayList<>();
        List<IMonsterMove> bulbasaurMoves = new ArrayList<>();
        List<IMonsterMove> charmanderMoves = new ArrayList<>();

        alpacaMoves.add(new MockMonsterMove("Spit", MonsterType.WATER, 10, "/sounds/Spit.ogg"));
        alpacaMoves.add(new MockMonsterMove("Trample", MonsterType.EARTH, 15, "/sounds/Trample.ogg"));
        alpacaMoves.add(new MockMonsterMove("Horn Blast", MonsterType.EARTH, 25, "/sounds/Horn Blast.ogg"));
        alpacaMoves.add(new MockMonsterMove("Fire Breath", MonsterType.FIRE, 50, "/sounds/Fire Breath.ogg"));
        monsters.add(new MockMonster(
                "Alpaca",
                40,
                10,
                20,
                5,
                "/monsters/alpacaF.png",
                "/monsters/alpacaB.png",
                MonsterType.EARTH,
                alpacaMoves
        ));

        eelMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        eelMoves.add(new MockMonsterMove("Zap", MonsterType.LIGHTNING, 10, "/sounds/Zap.ogg"));
        eelMoves.add(new MockMonsterMove("Water Blast", MonsterType.WATER, 30, "/sounds/Water Blast.ogg"));
        monsters.add(new MockMonster(
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
