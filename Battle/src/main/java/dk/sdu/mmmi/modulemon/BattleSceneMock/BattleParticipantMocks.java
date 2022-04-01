package dk.sdu.mmmi.modulemon.BattleSceneMock;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.Battle.BattleParticipant;
import dk.sdu.mmmi.modulemon.BattleScene.OSGiFileHandle;
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

        eelMoves.add(new MockMonsterMove("Tackle", MonsterType.AIR, 5));
        eelMoves.add(new MockMonsterMove("Zap", MonsterType.LIGHTNING, 10));
        eelMoves.add(new MockMonsterMove("Egg Bomb", MonsterType.GRASS, 20));
        eelMoves.add(new MockMonsterMove("Water Blast", MonsterType.WATER, 30));
        monsters.add(new MockMonster(
                "Eel",
                30,
                10,
                20,
                5,
                "/monsters/eelF.png",
                "/monsters/eelB.png",
                MonsterType.WATER,
                eelMoves
        ));


        alpacaMoves.add(new MockMonsterMove("Spit", MonsterType.WATER, 10));
        alpacaMoves.add(new MockMonsterMove("Trample", MonsterType.EARTH, 15));
        alpacaMoves.add(new MockMonsterMove("Horn Blast", MonsterType.EARTH, 25));
        alpacaMoves.add(new MockMonsterMove("Fire Breath", MonsterType.FIRE, 50));
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
        List<IMonsterMove> slowpokeMoves = new ArrayList<>();
        List<IMonsterMove> squrtleMoves = new ArrayList<>();
        List<IMonsterMove> eelMoves = new ArrayList<>();
        List<IMonsterMove> alpacaMoves = new ArrayList<>();




        alpacaMoves.add(new MockMonsterMove("Spit", MonsterType.WATER, 10));
        alpacaMoves.add(new MockMonsterMove("Trample", MonsterType.EARTH, 15));
        alpacaMoves.add(new MockMonsterMove("Horn Blast", MonsterType.EARTH, 25));
        alpacaMoves.add(new MockMonsterMove("Fire Breath", MonsterType.FIRE, 50));
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
        eelMoves.add(new MockMonsterMove("Zap", MonsterType.LIGHTNING, 10));
        eelMoves.add(new MockMonsterMove("Water Blast", MonsterType.WATER, 30));
        monsters.add(new MockMonster(
                "Eel",
                10,
                10,
                20,
                5,
                "/monsters/eelF.png",
                "/monsters/eelB.png",
                MonsterType.WATER,
                eelMoves
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
