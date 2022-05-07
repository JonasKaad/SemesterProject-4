package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    private static List<IMonsterRegistry> monsterRegistryList = new CopyOnWriteArrayList<>();

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        float x =  3014;
        float y =  1984;

        Entity player = new Player();
        PositionPart positionPart = new PositionPart(x, y);
        player.add(positionPart);
        player.add(new MovingPart());
        player.add(new InteractPart(positionPart, 1));
        Texture upSprite = AssetLoader.getInstance().getTextureAsset("/assets/main-char-up5.png", Player.class);
        Texture downSprite = AssetLoader.getInstance().getTextureAsset("/assets/main-char-down5.png", Player.class);
        Texture leftSprite = AssetLoader.getInstance().getTextureAsset("/assets/main-char-left5.png", Player.class);
        Texture rightSprite = AssetLoader.getInstance().getTextureAsset("/assets/main-char-right5.png", Player.class);
        player.add(new SpritePart(upSprite, downSprite, leftSprite, rightSprite));
        IMonsterRegistry monsterRegistry = monsterRegistryList.get(0);

        List<IMonster> monsterList = new ArrayList<>();
        monsterList.add(monsterRegistry.getMonster(0));
        monsterList.add(monsterRegistry.getMonster(1));
        monsterList.add(monsterRegistry.getMonster(2));
        monsterList.add(monsterRegistry.getMonster(3));
        monsterList.add(monsterRegistry.getMonster(4));
        monsterList.add(monsterRegistry.getMonster(5));
        player.add(new MonsterTeamPart(monsterList));

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }


    public void addMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistryList.add(registry);
    }

    // Used for tests
    public void setMonsterRegistryList(List<IMonsterRegistry> monsterRegistryList) {
        this.monsterRegistryList = monsterRegistryList;
    }

    public void removeMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistryList.remove(registry);
    }

}
