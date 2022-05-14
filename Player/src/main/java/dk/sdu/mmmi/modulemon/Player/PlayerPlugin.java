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
import dk.sdu.mmmi.modulemon.common.data.GameKeys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlayerPlugin implements IGamePluginService {
    private static final int TILE_SIZE = 64;
    private Entity player;
    private static IMonsterRegistry monsterRegistry;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        int xOffsetCorrection = 7;
        int yOffsetCorrection = 20;
        int tilemapXPos = 5;
        int tilemapYPos = 44;

        float x = (tilemapXPos)* TILE_SIZE + xOffsetCorrection;
        float y =  (TILE_SIZE*(TILE_SIZE-1) - (tilemapYPos) * TILE_SIZE ) + yOffsetCorrection;

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
        Queue<String> playerLines = new LinkedList<>();
        playerLines.add("Alright, lets battle!");
        player.add(new TextDisplayPart(playerLines));
        addMonsterTeam(player, gameData);

        return player;
    }

    private void addMonsterTeam(Entity entity, GameData gameData) {
        List<IMonster> monsterList = new ArrayList<>();
        if(gameData != null && gameData.getKeys().isDown(GameKeys.LEFT_CTRL))
            monsterList.add(monsterRegistry.getMonster(6 % monsterRegistry.getMonsterAmount())); //God
        entity.add(new MonsterTeamPart(monsterList));
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }


    public void setMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistry = registry;
        if (player != null) {
            addMonsterTeam(player, null);
        }
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
        if (player != null) {
            player.remove(MonsterTeamPart.class);
        }
    }

}
