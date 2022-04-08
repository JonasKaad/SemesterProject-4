package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;
import dk.sdu.mmmi.modulemon.common.drawing.OSGiFileHandle;
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

        float x =  3008;//gameData.getDisplayWidth() / 2f;
        float y =  2048;//gameData.getDisplayHeight() / 2f;

        Entity player = new Player();
        player.add(new PositionPart(x, y));
        player.add(new MovingPart());
        Texture upSprite = new Texture(new OSGiFileHandle("/assets/main-char-up5.png", Player.class));
        Texture downSprite = new Texture(new OSGiFileHandle("/assets/main-char-down5.png", Player.class));
        Texture leftSprite = new Texture(new OSGiFileHandle("/assets/main-char-left5.png", Player.class));
        Texture rightSprite = new Texture(new OSGiFileHandle("/assets/main-char-right5.png", Player.class));
        player.add(new SpritePart(upSprite, downSprite, leftSprite, rightSprite));
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
