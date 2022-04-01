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
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        float x =  3000;//gameData.getDisplayWidth() / 2f;
        float y =  2000;//gameData.getDisplayHeight() / 2f;

        Entity player = new Player();
        player.add(new PositionPart(x, y));
        player.add(new MovingPart());
        player.add(new SpritePart("/assets/main-char-up5.png",
                "/assets/main-char-down5.png",
                "/assets/main-char-left5.png",
                "/assets/main-char-right5.png"));
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
