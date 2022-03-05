package dk.sdu.mmmi.modulemon.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.managers.GameKeys;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;


public class PlayState extends GameState {
	
	private ShapeRenderer sr;



	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

	}
	
	public void update(float dt) {
		// user input
		handleInput();

	}
	
	public void draw() {

	}
	
	public void handleInput() {

	}

	
	public void dispose() {

	}
	
}









