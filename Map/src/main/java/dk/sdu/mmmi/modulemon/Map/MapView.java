package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.data.*;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MapView extends ApplicationAdapter implements IGameViewService {
    private Texture img;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera cam;
    private float mapLeft;
    private float mapRight;
    private float mapBottom;
    private float mapTop;

    float playerPosX;
    float playerPosY;
    SpriteBatch spriteBatch;
    private static World world = new World();
    private final GameData gameData = new GameData();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();

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
        mapLeft = (cam.viewportWidth / 2f);
        mapRight = mapWidth * tilePixelWidth - (cam.viewportWidth / 2f);
        mapBottom = 360;
        mapTop = mapBottom + mapHeight * tilePixelHeight - (cam.viewportWidth / 2f) - 80;
        spriteBatch = new SpriteBatch();
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);

    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }
    }

    private Texture getSpriteTexture(String spriteString) {
        return new Texture(new OSGiFileHandle(spriteString));

    }

    @Override
    public void draw(GameData gameData) {
        tiledMapRenderer.setView(Game.cam);
        tiledMapRenderer.render();

        for (Entity entity : world.getEntities()) {
            if (entity.getSpriteString() != null) {
                Texture sprite = getSpriteTexture(entity.getSpriteString());
                //System.out.println("My sprite is" + sprite);
                //spriteBatch.setProjectionMatrix(Game.cam.combined);
                spriteBatch.begin();
                spriteBatch.draw(sprite, entity.getPosX(), entity.getPosY());
                spriteBatch.end();

                System.out.println(entity.getClass());
                if (entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class) {
                    playerPosX = entity.getPosX() * 1;
                    playerPosY = entity.getPosY() * 1;
                    cam.position.set(playerPosX + cam.viewportWidth / 2f, playerPosY + cam.viewportHeight / 2f, 0);
                }
            } else {
                //System.out.println("no fucking file");
                //System.out.println("spritestring is:" + entity.getSpriteString());
            }
        }

    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
/*
        if (gameData.getKeys().isDown(GameKeys.DOWN)) Game.cam.translate(0, -32);
        if (gameData.getKeys().isDown(GameKeys.UP)) Game.cam.translate(0, 32);
        if (gameData.getKeys().isDown(GameKeys.LEFT)) Game.cam.translate(-32, 0);
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) Game.cam.translate(32, 0);

 */

        if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
            cam.position.set(gameData.getDisplayWidth() / 2f, gameData.getDisplayHeight() / 2f, 0);
            gameStateManager.setDefaultState();
        }
    }

    @Override
    public void dispose () {
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        super.dispose();
    }

    public void addEntityProcessingService (IEntityProcessingService eps){
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService (IEntityProcessingService eps){
        this.entityProcessorList.remove(eps);
    }

    public void addGamePluginService (IGamePluginService plugin){
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);
    }

    public void removeGamePluginService (IGamePluginService plugin){
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }
}