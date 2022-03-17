package dk.sdu.mmmi.modulemon.managers;

import dk.sdu.mmmi.modulemon.battleview.BattleView;
import dk.sdu.mmmi.modulemon.gamestates.GameState;
import dk.sdu.mmmi.modulemon.gamestates.MenuState;
import dk.sdu.mmmi.modulemon.gamestates.PlayState;

public class GameStateManager {

	private GameState gameState;

	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int BATTLE = 2;

	public GameStateManager() {
		setState(MENU);
	}
	
	public void setState(int state) {
		if (gameState != null) gameState.dispose();
		if (state == MENU) {
			gameState = new MenuState(this);
		} else if (state == PLAY) {
			gameState = new PlayState(this);
		} else if (state == BATTLE) {
			gameState = new BattleView(this);
		}
	}
	
	public void update(float dt) {
		gameState.update(dt);
		gameState.handleInput();
	}
	
	public void draw() {
		gameState.draw();
	}
	
}











