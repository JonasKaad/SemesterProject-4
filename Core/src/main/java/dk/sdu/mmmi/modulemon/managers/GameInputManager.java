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
        handleButtons(k, true);
        return true;
    }

    public boolean keyUp(int k) {
        handleButtons(k, false);
        return true;
    }

    private void handleButtons(int k, boolean state) {
        if (k == Input.Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, state);
        }
        if (k == Input.Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, state);
        }
        if (k == Input.Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, state);
        }
        if (k == Input.Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, state);
        }
        if (k == Input.Keys.ENTER
                || k == Input.Keys.SPACE
                || k == Input.Keys.BUTTON_A) {
            gameData.getKeys().setKey(GameKeys.ACTION, state);
        }
        if (k == Input.Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, state);
        }
        if (k == Input.Keys.K) {
            gameData.getKeys().setKey(GameKeys.K, state);
        }
        if (k == Input.Keys.CONTROL_LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT_CTRL, state);
        }
        if (k == Input.Keys.ESCAPE
                || k == Input.Keys.BACKSPACE
                || k == Input.Keys.BUTTON_B
                || k == Input.Keys.BUTTON_START) {
            gameData.getKeys().setKey(GameKeys.BACK, state);
        }
    }
}
