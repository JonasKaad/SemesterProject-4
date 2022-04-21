package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import dk.sdu.mmmi.modulemon.CommonTest.GdxTestIntercepter;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.modulemon.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static dk.sdu.mmmi.modulemon.common.data.GameKeys.UP;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GdxTestIntercepter.class)
class PlayerTest {
    GameData gameData;
    World world;

    @BeforeEach
    void setUp() {
        gameData = new GameData();
        world = new World();
        gameData.setDelta(1);
        gameData.setDisplayHeight(100);
        gameData.setDisplayWidth(100);
    }

    @Test
    void playerPluginShouldBeStarted(){
        PlayerPlugin playerPlugin = new PlayerPlugin();

        playerPlugin.start(gameData, world);

        assertEquals(1, world.getEntities(Player.class).size());
    }

    @Test
    void playerShouldMove() {
        PlayerPlugin playerPlugin = new PlayerPlugin();

        playerPlugin.start(gameData, world);
        Entity player = world.getEntities(Player.class).get(0); //There's only one entity
        MovingPart movPart = player.getPart(MovingPart.class);
        PositionPart posPart = player.getPart(PositionPart.class);

        float beforeMoving = posPart.getY();


        gameData.getKeys().setKey(UP, true);
        movPart.setUp(gameData.getKeys().isDown(UP));
        movPart.process(gameData, player);

        float afterMoving = posPart.getY();

        assertNotEquals(beforeMoving, afterMoving);
    }

    @Test
    void playerShouldNotMove(){
        PlayerPlugin playerPlugin = new PlayerPlugin();
        playerPlugin.start(gameData, world);
        Entity player = world.getEntities(Player.class).get(0); //There's only one entity
        MovingPart movPart = player.getPart(MovingPart.class);
        PositionPart posPart = player.getPart(PositionPart.class);

        float beforeMoving = posPart.getY();


        movPart.setUp(gameData.getKeys().isDown(UP));
        movPart.process(gameData, player);

        float afterMoving = posPart.getY();

        assertEquals(beforeMoving, afterMoving);
    }
}