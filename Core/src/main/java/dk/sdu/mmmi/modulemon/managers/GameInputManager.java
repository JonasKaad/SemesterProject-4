package dk.sdu.mmmi.modulemon.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;

public class GameInputManager extends InputAdapter {

    private final GameData gameData;

    public GameInputManager(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        if (k == Input.Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if (k == Input.Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if (k == Input.Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if (k == Input.Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if (k == Input.Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if (k == Input.Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, true);
        }
        return true;
    }


    public boolean keyUp(int k) {
        if(k == Input.Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if(k == Input.Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if(k == Input.Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if(k == Input.Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if(k == Input.Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if (k == Input.Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, false);
        }
        return true;
    }
}
