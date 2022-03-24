package dk.sdu.mmmi.modulemon.Monster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterPlugin extends Entity implements IGamePluginService {
    private Entity monster;

    MonsterMove moveOne = new MonsterMove("Air Burst", 15, MonsterType.AIR);
    MonsterMove moveTwo = new MonsterMove("Flame Fork", 30, MonsterType.FIRE);
    MonsterMove moveThree = new MonsterMove("Ground-shatter", 25, MonsterType.EARTH);
    MonsterMove moveFour = new MonsterMove("Electrocution", 40, MonsterType.LIGHTNING);

    List<IMonsterMove> moveList = Arrays.asList(moveOne, moveTwo, moveThree, moveFour);
    ArrayList<IMonsterMove> monsterMoves = new ArrayList<>(moveList);

    // For some reason using Libgdx gives a null pointer on the texture, uncommented for now
    /*
    Texture texture1 = new Texture(Gdx.files.internal("assets/monsters/chicken.png"));
    Texture texture2 = new Texture(Gdx.files.internal("assets/monsters/pig.png"));
    IMonster chickenMonsterWithTexture = new Monster("Pollastro", MonsterType.AIR, 80, 22, 44, 66, texture1, monsterMoves);
    IMonster pigMonsterWithTexture = new Monster("El Puerco", MonsterType.EARTH, 120, 33, 55, 77, texture2, monsterMoves);
    */
    IMonster chickenMonster = new Monster("Pollastro", MonsterType.AIR, 80, 22, 44, 66, monsterMoves);
    IMonster pigMonster = new Monster("El Puerco", MonsterType.EARTH, 120, 33, 55, 77, monsterMoves);



    public IMonster getChickenMonster() {
        return chickenMonster;
    }

    public IMonster getPigMonster() {
        return pigMonster;
    }


    public static void main(String[] args) {
        Image image = new Image(new Texture("../resources/pig.png"));
        System.out.println(image);
    }

    @Override
    public void start(GameData gameData, World world) {
        monster = (Entity) getPigMonster();
        world.addEntity(monster);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity monster : world.getEntities()){
            if (monster.getClass() == Monster.class) {
                world.removeEntity(monster);
            }
        }
    }
}
