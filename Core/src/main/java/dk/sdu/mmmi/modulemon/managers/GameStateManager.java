package dk.sdu.mmmi.modulemon.managers;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;
import dk.sdu.mmmi.modulemon.gamestates.GameState;
import dk.sdu.mmmi.modulemon.gamestates.MenuState;

public class GameStateManager implements IGameStateManager {
	private IGameViewService currentGameState;

	public GameStateManager() {
		setDefaultState();
	}
	
	public void setState(IGameViewService state, boolean disposeCurrent) {
		if(currentGameState != null && disposeCurrent) currentGameState.dispose();
		System.out.println(String.format("Changed state to: %s", state.getClass().getName()));
		currentGameState = state;
		currentGameState.init(this);
	}

	public void setState(IGameViewService state){
		setState(state, true);
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











