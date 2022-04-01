package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.data.*;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class MapView implements IGameViewService {
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private boolean isPaused;
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
        cam.position.set(mapRight /2f, mapTop / 2f, 0);
      
        // Sprites
        spriteBatch = new SpriteBatch();
      
        // Pausing
        isPaused = false;
        shapeRenderer = new ShapeRenderer();

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
                //System.out.println("before drawing:" + entity.getPosX() + "  -  " + entity.getPosY());

                spriteBatch.setProjectionMatrix(cam.combined);
                spriteBatch.begin();
                spriteBatch.draw(sprite, entity.getPosX(), entity.getPosY());
                spriteBatch.end();

                //System.out.println(entity.getClass());
                if (entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class) {
                    playerPosX = entity.getPosX() * 1;
                    playerPosY = entity.getPosY() * 1;
                    if(playerPosY > mapBottom && playerPosY < mapTop){
                        cam.position.set(cam.position.x, playerPosY, 0);

                        //cam.position.set(playerPosX + cam.viewportWidth / 2f, playerPosY + cam.viewportHeight / 2f, 0);
                        cam.update();
                    }
                    if (playerPosX > mapLeft && playerPosX < mapRight) {
                        cam.position.set(playerPosX, cam.position.y, 0);
                        cam.update();
                    }
                    //System.out.println("following cam: " + playerPosX + "  -  " + playerPosY);
                }
            } else {
                //System.out.println("spritestring is:" + entity.getSpriteString());
            }
        }
        if(isPaused){
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.setColor(Color.WHITE);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Vector3 camCoordinates = cam.unproject(new Vector3(500, 500, 0));
            shapeRenderer.rect(camCoordinates.x, camCoordinates.y, 100, 100);
            shapeRenderer.end();
        }
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if(isPaused){
            if(gameData.getKeys().isPressed(GameKeys.E)){
                isPaused = false;
            }
            return;
        }
      /*
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
        */
        if(gameData.getKeys().isPressed(GameKeys.E)){
            isPaused = true;
        }
        if (gameData.getKeys().isPressed(GameKeys.ENTER)){
            cam.position.set(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2, 0);
            gameStateManager.setDefaultState();
        }
    }

    @Override
    public void dispose() {
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2, 0);
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
