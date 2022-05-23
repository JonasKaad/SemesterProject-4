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
    private IGameViewService currentGameView;
    private IGameSettings settings;
    private SpriteBatch spriteBatch;
    private Image lastScreenBeforeChange;

    public GameViewManager() {
        setDefaultView();
        spriteBatch = new SpriteBatch();
    }

    public void setSettings(IGameSettings settings) {
        this.settings = settings;
        setDefaultView();
    }

    public void setView(IGameViewService view, boolean disposeCurrent) {
        //Take screenshot in order to not have flicking background, when new scene has transperant backgrounds.
        lastScreenBeforeChange = new Image(ScreenUtils.getFrameBufferTexture());

        if (currentGameView != null && disposeCurrent) currentGameView.dispose();
        System.out.println(String.format("Changed state to: %s", view.getClass().getName()));
        currentGameView = view;
        currentGameView.init(this);
    }

    public void setView(IGameViewService view) {
        setView(view, true);
    }

    public void update(GameData gameData) {
        currentGameView.update(gameData, this);
        currentGameView.handleInput(gameData, this);
    }

    public void draw(GameData gameData) {
        if (lastScreenBeforeChange != null) {
            spriteBatch.begin();
            lastScreenBeforeChange.draw(spriteBatch, 1f);
            spriteBatch.end();
        }
        currentGameView.draw(gameData);
    }

    public IGameViewService getCurrentGameView() {
        return currentGameView;
    }

    @Override
    public void setDefaultView() {
        setView(new MenuView(this.settings));
    }
}











