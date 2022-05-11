package dk.sdu.mmmi.modulemon.Player;

import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonTest.GdxTestIntercepter;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MovingPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

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
        gameData.setDelta(10);
        gameData.setDisplayHeight(100);
        gameData.setDisplayWidth(100);
    }

    @Test
    void playerPluginShouldBeStarted(){
        PlayerPlugin playerPlugin = new PlayerPlugin();

        IMonsterRegistry monsterRegistryMock = mock(IMonsterRegistry.class);
        IMonster monsterMock = mock(IMonster.class);
        when(monsterRegistryMock.getMonster(0)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(1)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(2)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(3)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(4)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(5)).thenReturn(monsterMock);
        playerPlugin.setMonsterRegistryService(monsterRegistryMock);

        playerPlugin.start(gameData, world);

        assertEquals(1, world.getEntities(Player.class).size());
    }

    @Test
    void playerShouldMove() {
        PlayerPlugin playerPlugin = new PlayerPlugin();


        IMonsterRegistry monsterRegistryMock = mock(IMonsterRegistry.class);
        IMonster monsterMock = mock(IMonster.class);
        when(monsterRegistryMock.getMonster(0)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(1)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(2)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(3)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(4)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(5)).thenReturn(monsterMock);
        playerPlugin.setMonsterRegistryService(monsterRegistryMock);

        playerPlugin.start(gameData, world);
        Entity player = world.getEntities(Player.class).get(0); //There's only one entity
        MovingPart movPart = player.getPart(MovingPart.class);
        PositionPart posPart = player.getPart(PositionPart.class);
        IMapView map = mock(IMapView.class);
        when(map.isCellBlocked(anyFloat(), anyFloat())).thenReturn(false);
        when(map.getMapTop()).thenReturn(0f);
        when(map.getMapLeft()).thenReturn(0f);
        when(map.getMapRight()).thenReturn(50000f);
        when(map.getMapBottom()).thenReturn(50000f);
      
        float beforeMoving = posPart.getTargetPos().y;


        gameData.getKeys().setKey(UP, true);
        movPart.setUp(gameData.getKeys().isDown(UP));
        movPart.process(gameData, world, player);

        float afterMoving = posPart.getTargetPos().y;

        assertNotEquals(beforeMoving, afterMoving);
    }

    @Test
    void playerShouldNotMove(){
        PlayerPlugin playerPlugin = new PlayerPlugin();

        IMonsterRegistry monsterRegistryMock = mock(IMonsterRegistry.class);
        IMonster monsterMock = mock(IMonster.class);
        when(monsterRegistryMock.getMonster(0)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(1)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(2)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(3)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(4)).thenReturn(monsterMock);
        when(monsterRegistryMock.getMonster(5)).thenReturn(monsterMock);
        playerPlugin.setMonsterRegistryService(monsterRegistryMock);

        playerPlugin.start(gameData, world);
        Entity player = world.getEntities(Player.class).get(0); //There's only one entity
        MovingPart movPart = player.getPart(MovingPart.class);
        PositionPart posPart = player.getPart(PositionPart.class);

        float beforeMoving = posPart.getTargetPos().y;


        movPart.setUp(gameData.getKeys().isDown(UP));
        movPart.process(gameData, world, player);

        float afterMoving = posPart.getTargetPos().y;

        assertEquals(beforeMoving, afterMoving);
    }
}