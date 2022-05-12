package dk.sdu.mmmi.modulemon.MapEntities;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.InteracteePart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.SpritePart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IGamePluginService;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;
import java.util.List;

public class MapEntityPlugin implements IGamePluginService {
    private static final int TILE_SIZE = 64;
    private List<Entity> entities;
    private static IMonsterRegistry monsterRegistry;

    @Override
    public void start(GameData gameData, World world) {
        entities = new ArrayList<>();
        Entity vendingMachine1 = createVendingMachine(gameData, 17, 39);
        Entity healingMachine1 = createHealingMachine(gameData, 17, 53);

        world.addEntity(vendingMachine1);
        world.addEntity(healingMachine1);

        entities.add(vendingMachine1);
        entities.add(healingMachine1);
    }

    private Entity createVendingMachine(GameData gameData, int x, int y) {
        int xOffsetCorrection = 7;
        int yOffsetCorrection = 20;
        int tilemapXPos = x;
        int tilemapYPos = y;
        float actualX = (tilemapXPos)* TILE_SIZE + xOffsetCorrection;
        float actualY =  (TILE_SIZE*(TILE_SIZE-1) - (tilemapYPos) * TILE_SIZE ) + yOffsetCorrection;

        Entity vendingMachine = new VendingMachine();
        PositionPart positionPart = new PositionPart(actualX, actualY);
        Texture texture = AssetLoader.getInstance().getTextureAsset("/Textures/vendingmachine.png", getClass());
        SpritePart spritePart = new SpritePart(texture, texture, texture, texture);
        spritePart.setCurrentSprite(texture);
        InteracteePart interacteePart = new InteracteePart(VendingMachineEvent::new);

        vendingMachine.add(positionPart);
        vendingMachine.add(spritePart);
        vendingMachine.add(interacteePart);

        return vendingMachine;
    }

    private Entity createHealingMachine(GameData gameData, int x, int y) {
        int xOffsetCorrection = 7;
        int yOffsetCorrection = 20;
        int tilemapXPos = x;
        int tilemapYPos = y;

        float actualX = (tilemapXPos)* TILE_SIZE + xOffsetCorrection;
        float actualY =  (TILE_SIZE*(TILE_SIZE-1) - (tilemapYPos) * TILE_SIZE ) + yOffsetCorrection;

        Entity healingMachine = new HealingMachine();
        PositionPart positionPart = new PositionPart(actualX, actualY);
        Texture texture = AssetLoader.getInstance().getTextureAsset("/Textures/healingmachine.png", getClass());
        SpritePart spritePart = new SpritePart(texture, texture, texture, texture);
        spritePart.setCurrentSprite(texture);
        InteracteePart interacteePart = new InteracteePart(HealingMachineEvent::new);

        healingMachine.add(positionPart);
        healingMachine.add(spritePart);
        healingMachine.add(interacteePart);
        return healingMachine;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        entities.forEach(world::removeEntity);
    }

    public void setMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistry = registry;
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
    }
}
