package dk.sdu.mmmi.modulemon.Player;

import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 6;
        float x = 0; //gameData.getDisplayWidth() / 2f;
        float y = 0; //gameData.getDisplayHeight() / 2f;

        Entity playerShip = new Player();
        playerShip.add(new MovingPart());
        playerShip.add(new PositionPart(x, y));
        playerShip.add(new SpritePart("/assets/main-char-up5.png",
                "/assets/main-char-down5.png",
                "/assets/main-char-left5.png",
                "/assets/main-char-right5.png"));
        
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
