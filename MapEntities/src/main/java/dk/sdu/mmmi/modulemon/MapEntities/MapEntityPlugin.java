package dk.sdu.mmmi.modulemon.MapEntities;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.InteracteePart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.SpritePart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IGamePluginService;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MapEntityPlugin implements IGamePluginService {
    private static final int TILE_SIZE = 64;
    private List<Entity> entities;
    private static IMonsterRegistry monsterRegistry;

    @Override
    public void start(GameData gameData, World world) {
        entities = new ArrayList<>();
        addVendingMachines(gameData, world);
    }

    private void addVendingMachines(GameData gameData, World world) {
        ArrayList<IMonster> monstersInVending1 = new ArrayList<>();
        // Fugl Eel
        if (monsterRegistry != null) {
            monstersInVending1.add(monsterRegistry.getMonster(2 % monsterRegistry.getMonsterAmount()));
            monstersInVending1.add(monsterRegistry.getMonster(1 % monsterRegistry.getMonsterAmount()));
        }
        Entity vendingMachine1 = createVendingMachine(gameData, 17, 39, monstersInVending1);
        Entity healingMachine1 = createHealingMachine(gameData, 17, 53);

        // Dog Cat
        ArrayList<IMonster> monstersInVending2 = new ArrayList<>();
        if (monsterRegistry != null) {
            monstersInVending2.add(monsterRegistry.getMonster(5 % monsterRegistry.getMonsterAmount()));
            monstersInVending2.add(monsterRegistry.getMonster(4 % monsterRegistry.getMonsterAmount()));
        }
        Entity vendingMachine2 = createVendingMachine(gameData, 35, 42, monstersInVending2);
        Entity healingMachine2 = createHealingMachine(gameData, 35, 50);

        // Alpaca Eel Cat
        ArrayList<IMonster> monstersInVending3 = new ArrayList<>();
        if (monsterRegistry != null) {
            monstersInVending3.add(monsterRegistry.getMonster(0 % monsterRegistry.getMonsterAmount()));
            monstersInVending3.add(monsterRegistry.getMonster(1 % monsterRegistry.getMonsterAmount()));
            monstersInVending3.add(monsterRegistry.getMonster(4 % monsterRegistry.getMonsterAmount()));
        }
        Entity vendingMachine3 = createVendingMachine(gameData, 50, 21, monstersInVending3);
        Entity healingMachine3 = createHealingMachine(gameData, 60, 21);

        // Mole Secret SHH! id: 3
        ArrayList<IMonster> monstersInSecretVending = new ArrayList<>();
        if (monsterRegistry != null) {
            monstersInSecretVending.add(monsterRegistry.getMonster(3 % monsterRegistry.getMonsterAmount()));
            monstersInSecretVending.add(monsterRegistry.getMonster(7 % monsterRegistry.getMonsterAmount()));
        }
        Entity secretVendingMachine = createVendingMachine(gameData, 37, 21, monstersInSecretVending);
        Entity healingMachine4 = createHealingMachine(gameData, 22, 15);

        world.addEntity(vendingMachine1);
        world.addEntity(healingMachine1);
        world.addEntity(vendingMachine2);
        world.addEntity(healingMachine2);
        world.addEntity(vendingMachine3);
        world.addEntity(healingMachine3);
        world.addEntity(secretVendingMachine);
        world.addEntity(healingMachine4);

        entities.add(vendingMachine1);
        entities.add(healingMachine1);
        entities.add(vendingMachine2);
        entities.add(healingMachine2);
        entities.add(vendingMachine3);
        entities.add(healingMachine3);
        entities.add(secretVendingMachine);
    }

    private Entity createVendingMachine(GameData gameData, int x, int y, List<IMonster> monsters) {
        int xOffsetCorrection = 7;
        int yOffsetCorrection = 20;
        int tilemapXPos = x;
        int tilemapYPos = y;
        float actualX = (tilemapXPos) * TILE_SIZE + xOffsetCorrection;
        float actualY = (TILE_SIZE * (TILE_SIZE - 1) - (tilemapYPos) * TILE_SIZE) + yOffsetCorrection;

        Entity vendingMachine = new VendingMachine();
        PositionPart positionPart = new PositionPart(actualX, actualY);
        Texture texture = AssetLoader.getInstance().getTextureAsset("/Textures/vendingmachine.png", getClass());
        SpritePart spritePart = new SpritePart(texture, texture, texture, texture);
        spritePart.setCurrentSprite(texture);
        UUID machineUuid = UUID.randomUUID();
        InteracteePart interacteePart = new InteracteePart((e) -> new VendingMachineEvent(e, monsters, machineUuid));

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

        float actualX = (tilemapXPos) * TILE_SIZE + xOffsetCorrection;
        float actualY = (TILE_SIZE * (TILE_SIZE - 1) - (tilemapYPos) * TILE_SIZE) + yOffsetCorrection;

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
        entities.clear();
    }

    public void setMonsterRegistryService(IMonsterRegistry registry) {
        this.monsterRegistry = registry;
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
    }
}
