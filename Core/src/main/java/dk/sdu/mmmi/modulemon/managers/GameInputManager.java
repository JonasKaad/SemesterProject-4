package dk.sdu.mmmi.modulemon.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputManager extends InputAdapter {
    public boolean keyDown(int k) {
        if (k == Input.Keys.UP) {
            GameKeys.setKey(GameKeys.UP, true);
        }
        if (k == Input.Keys.LEFT) {
            GameKeys.setKey(GameKeys.LEFT, true);
        }
        if (k == Input.Keys.DOWN) {
            GameKeys.setKey(GameKeys.DOWN, true);
        }
        if (k == Input.Keys.RIGHT) {
            GameKeys.setKey(GameKeys.RIGHT, true);
        }
        if (k == Input.Keys.ENTER) {
            GameKeys.setKey(GameKeys.ENTER, true);
        }
        if (k == Input.Keys.E) {
            GameKeys.setKey(GameKeys.E, true);
        }
        return true;
    }


    public boolean keyUp(int k) {
        if(k == Input.Keys.UP) {
            GameKeys.setKey(GameKeys.UP, false);
        }
        if(k == Input.Keys.LEFT) {
            GameKeys.setKey(GameKeys.LEFT, false);
        }
        if(k == Input.Keys.DOWN) {
            GameKeys.setKey(GameKeys.DOWN, false);
        }
        if(k == Input.Keys.RIGHT) {
            GameKeys.setKey(GameKeys.RIGHT, false);
        }
        if(k == Input.Keys.ENTER) {
            GameKeys.setKey(GameKeys.ENTER, false);
        }
        if (k == Input.Keys.E) {
            GameKeys.setKey(GameKeys.E, false);
        }
        return true;
    }
}
