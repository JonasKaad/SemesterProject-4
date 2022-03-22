package dk.sdu.mmmi.modulemon.managers;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;
import dk.sdu.mmmi.modulemon.gamestates.GameState;
import dk.sdu.mmmi.modulemon.gamestates.MenuState;

public class GameStateManager implements IGameStateManager {
	private IGameViewService currentGameState;

	public static final int DEFAULT_VIEW = 0;
	
	public GameStateManager() {
		setDefaultState();
	}
	
	public void setState(IGameViewService state) {
		if(currentGameState != null) currentGameState.dispose();
		System.out.println(String.format("Changed state to: %s", state.getClass().getName()));
		currentGameState = state;
		currentGameState.init();
	}
	
	public void update(GameData gameData) {
		currentGameState.update(gameData, this);
		currentGameState.handleInput(gameData, this);
	}
	
	public void draw(GameData gameData) {
		currentGameState.draw(gameData);
	}

	public IGameViewService getCurrentGameState() {
		return currentGameState;
	}

	@Override
	public void setDefaultState() {
		setState(new MenuState());
	}
}











