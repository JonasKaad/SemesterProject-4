package dk.sdu.mmmi.modulemon.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameViewManager;
import dk.sdu.mmmi.modulemon.common.services.IGameSettings;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;
import dk.sdu.mmmi.modulemon.gameviews.MenuView;

public class GameViewManager implements IGameViewManager {
    private IGameViewService currentGameState;
    private IGameSettings settings;
    private SpriteBatch spriteBatch;
    private Image lastScreenBeforeChange;

    public GameViewManager() {
        setDefaultState();
        spriteBatch = new SpriteBatch();
    }

    public void setSettings(IGameSettings settings) {
        this.settings = settings;
        setDefaultState();
    }

    public void setState(IGameViewService state, boolean disposeCurrent) {
        //Take screenshot in order to not have flicking background, when new scene has transperant backgrounds.
        lastScreenBeforeChange = new Image(ScreenUtils.getFrameBufferTexture());

        if (currentGameState != null && disposeCurrent) currentGameState.dispose();
        System.out.println(String.format("Changed state to: %s", state.getClass().getName()));
        currentGameState = state;
        currentGameState.init(this);
    }

    public void setState(IGameViewService state) {
        setState(state, true);
    }

    public void update(GameData gameData) {
        currentGameState.update(gameData, this);
        currentGameState.handleInput(gameData, this);
    }

    public void draw(GameData gameData) {
        if (lastScreenBeforeChange != null) {
            spriteBatch.begin();
            lastScreenBeforeChange.draw(spriteBatch, 1f);
            spriteBatch.end();
        }
        currentGameState.draw(gameData);
    }

    public IGameViewService getCurrentGameState() {
        return currentGameState;
    }

    @Override
    public void setDefaultState() {
        setState(new MenuView(this.settings));
    }
}











