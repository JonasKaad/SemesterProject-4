package dk.sdu.mmmi.modulemon.Battle;

import dk.sdu.mmmi.modulemon.BattleScene.BattleView;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents.InfoBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonTest.GdxTestIntercepter;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.invocation.Invocation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GdxTestIntercepter.class)
public class BattleViewTest {

    @Test
    public void BattleView_Construction_ShouldNotThrow() {
        // Arrange / Act
        Executable act = () -> new BattleView();

        // Assert
        assertDoesNotThrow(act, "A battleview with an empty constructor should be made without crashing");
    }

    @Test
    public void BattleView_EmptyBattleSimulation_ShouldNotThrow() {
        // Arrange
        BattleView bw = new BattleView();
        bw.init(mock(IGameStateManager.class));
        GameData gameData = new GameData();

        // Act
        Executable act = () -> {
            bw.update(gameData, null);
            bw.handleInput(gameData, null);
            bw.draw(gameData);
        };

        // Assert
        assertDoesNotThrow(act, "If no battle simulation is set, the game should not crash even if the view is changed to the battle view");
    }

    @Test
    public void BattleView_UnloadingBattleSimulationOnRuntime_ShouldNotThrow() throws IOException, URISyntaxException {
        // Arrange
        BattleView battleView = new BattleView();
        battleView.init(mock(IGameStateManager.class));
        GameData gameData = new GameData();
        IBattleSimulation simulation = mock(IBattleSimulation.class);
        IBattleState battleState = mock(IBattleState.class);
        when(battleState.getPlayer()).thenReturn(mock(IBattleParticipant.class));
        when(battleState.getEnemy()).thenReturn(mock(IBattleParticipant.class));
        when(simulation.getState()).thenReturn(battleState);
        battleView.setBattleSimulation(simulation);

        // Act
        Executable act = () -> {
            //Simulating the game running
            battleView.update(gameData, null);
            battleView.handleInput(gameData, null);
            battleView.draw(gameData);

            battleView.removeBattleSimulation(simulation);

            battleView.update(gameData, null);
            battleView.handleInput(gameData, null);
            battleView.draw(gameData);
        };

        // Assert
        assertDoesNotThrow(act, "Removing the battle simulation on runtime should not crash!");
    }

    @Test
    public void BattleView_DrawWidthHeight_ShouldBeUpdatedByGameData() {
        // Arrange
        BattleView battleView = new BattleView();
        battleView.init(mock(IGameStateManager.class));
        GameData gameData = new GameData();
        gameData.setDisplayHeight(120);
        gameData.setDisplayWidth(100);

        BattleScene scene = new BattleScene();
        battleView.setBattleScene(scene);

        // Act / Assert
        battleView.update(gameData, null);
        battleView.draw(gameData);

        assertEquals(120, scene.getGameHeight());
        assertEquals(100, scene.getGameWidth());

        gameData.setDisplayHeight(220);
        gameData.setDisplayWidth(200);
        battleView.draw(gameData);
        assertEquals(220, scene.getGameHeight());
        assertEquals(200, scene.getGameWidth());
    }

    @Test
    public void BattleView_Init_ShouldStartBattle() {
        // Arrange
        BattleView battleView = new BattleView();
        battleView.init(mock(IGameStateManager.class));
        List<IMonster> playerMonsters = new ArrayList<>();
        List<IMonster> enemyMonsters = new ArrayList<>();

        IBattleSimulation simulation = mock(IBattleSimulation.class);
        when(simulation.getState()).thenReturn(mock(IBattleState.class));
        battleView.setBattleSimulation(simulation);

        // Act
        battleView.startBattle(playerMonsters, enemyMonsters, null);
        battleView.dispose();

        // Assert
        Collection<Invocation> invocationCollection = mockingDetails(simulation).getInvocations();
        boolean hasCalledStartBattle = invocationCollection.stream().anyMatch(x -> x.getMethod().getName().equalsIgnoreCase("startbattle"));
        assertTrue(hasCalledStartBattle, "Calling init() on battleView should call the simulation to start the battle");
    }

    @Test
    public void BattleView_TextEvent_ShouldWriteToScene() throws IOException, URISyntaxException {
        // Arrange
        BattleView battleView = new BattleView();
        battleView.init(mock(IGameStateManager.class));
        GameData gameData = new GameData();
        BattleScene scene = new BattleScene();

        IBattleSimulation simulation = mock(IBattleSimulation.class);
        IBattleState battleState = mock(IBattleState.class);
        when(battleState.getPlayer()).thenReturn(mock(IBattleParticipant.class));
        when(battleState.getEnemy()).thenReturn(mock(IBattleParticipant.class));
        when(simulation.getState()).thenReturn(battleState);
        when(simulation.getNextBattleEvent()).thenReturn(new InfoBattleEvent("Never gonna give you up!", null));
        battleView.setBattleSimulation(simulation);
        battleView.setBattleScene(scene);

        // Act
        battleView.update(gameData, null);

        // Assert
        assertEquals("Never gonna give you up!", scene.getTextToDisplay(), "A infobattleevent should show some text on the bottom of the scene");

        Collection<Invocation> invocationCollection = mockingDetails(simulation).getInvocations();
        boolean hasCalledGetEvent = invocationCollection.stream().anyMatch(x -> x.getMethod().getName().equalsIgnoreCase("getNextBattleEvent"));
        assertTrue(hasCalledGetEvent, "The battleview should call `getNextBattleEvent()` on every frame where there is no animations running");
    }

}
