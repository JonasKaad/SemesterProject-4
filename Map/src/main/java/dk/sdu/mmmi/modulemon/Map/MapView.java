package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;


public class MapView extends ApplicationAdapter implements IGameViewService {
    Texture img;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    StretchViewport viewport;
    OrthographicCamera cam;

    @Override
    public void init() {
        cam = Game.cam;
        tiledMap = new OSGiTmxLoader().load("/maps/SeasonalOverworld.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 4);
    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {

    }

    @Override
    public void draw(GameData gameData) {
        tiledMapRenderer.setView(Game.cam);
        tiledMapRenderer.render();
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if (gameData.getKeys().isDown(GameKeys.DOWN)) Game.cam.translate(0, -32);
        if (gameData.getKeys().isDown(GameKeys.UP)) Game.cam.translate(0, 32);
        if (gameData.getKeys().isDown(GameKeys.LEFT)) Game.cam.translate(-32, 0);
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) Game.cam.translate(32, 0);
        if(gameData.getKeys().isPressed(GameKeys.ENTER)){
            cam.position.set(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2, 0);
            gameStateManager.setDefaultState();
        }
    }
}
