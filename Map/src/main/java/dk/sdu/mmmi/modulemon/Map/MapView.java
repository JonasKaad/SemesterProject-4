package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;


public class MapView extends ApplicationAdapter implements IGameViewService {
    private Texture img;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera cam;
    private float mapLeft;
    private float mapRight;
    private float mapBottom;
    private float mapTop;

    @Override
    public void init() {
        cam = Game.cam;
        tiledMap = new OSGiTmxLoader().load("/maps/SeasonalOverworld.tmx");
        int scale = 4;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scale);

        // Setting bounds for map
        MapProperties properties = tiledMap.getProperties();
        int mapWidth = properties.get("width", Integer.class);
        int mapHeight = properties.get("height", Integer.class);
        int tilePixelWidth = 16 * scale;
        int tilePixelHeight = 16 * scale;
        mapLeft = (cam.viewportWidth/2f);
        mapRight = mapWidth * tilePixelWidth - (cam.viewportWidth/2f);
        mapBottom = 360;
        mapTop = mapBottom + mapHeight * tilePixelHeight - (cam.viewportWidth/2f) - 80;
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
        if (gameData.getKeys().isDown(GameKeys.DOWN) && cam.position.y > mapBottom) {
            Game.cam.translate(0, -16);
        }
        if (gameData.getKeys().isDown(GameKeys.UP) && cam.position.y < mapTop) {
            Game.cam.translate(0, 16);
        }
        if (gameData.getKeys().isDown(GameKeys.LEFT) && cam.position.x > mapLeft){
            Game.cam.translate(-16, 0);
        }
        if (gameData.getKeys().isDown(GameKeys.RIGHT) && cam.position.x < mapRight){
            Game.cam.translate(16, 0);
        }
        if (gameData.getKeys().isPressed(GameKeys.ENTER)){
            cam.position.set(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2, 0);
            gameStateManager.setDefaultState();
        }
    }

    @Override
    public void dispose() {
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2, 0);
        super.dispose();
    }
}
