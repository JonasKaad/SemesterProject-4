package dk.sdu.mmmi.modulemon.CommonMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.common.data.GameData;

public interface IMapEvent {
    boolean isEventDone();

    void start(GameData gameData);

    void update(GameData gameData);

    void draw(GameData gameData, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);

    void handleInput(GameData gameData);
}
