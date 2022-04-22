package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattle.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleCallback;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleResult;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleView;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;
import dk.sdu.mmmi.modulemon.Player.PlayerPlugin;
import dk.sdu.mmmi.modulemon.common.data.*;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;


public class MapView implements IGameViewService, IMapView {
    private TiledMap tiledMap;
    private TiledMapTileLayer overhangLayer;
    private BatchTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private Music mapMusic;
    private boolean isPaused;
    private TextUtils textUtils;
    private Rectangle pauseMenu;
    private String pauseMenuTitle = "GAME PAUSED";
    private String[] pauseActions = new String[]{"Resume", "Inventory", "Team", "Quit"};
    private int selectedOptionIndex = 0;
    private float mapLeft;
    private float mapRight;
    private float mapBottom;
    private float mapTop;
    private int tilePixelSize;

    float playerPosX;
    float playerPosY;
    SpriteBatch spriteBatch;
    private static World world = new World();
    private final GameData gameData = new GameData();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static Queue<Runnable> gdxThreadTasks = new LinkedList<>();

    private IGameStateManager gameStateManager;
    private IBattleView battleView;
    private MonsterTeamPart playerMonsters;

    @Override
    public void init(IGameStateManager gameStateManager) {
        mapMusic = Gdx.audio.newMusic(new OSGiFileHandle("/music/village_theme.ogg", MapView.class));
        tiledMap = new OSGiTmxLoader().load("/maps/SeasonalOverworld.tmx");
        overhangLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Top");
        int scale = 4;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scale);
        mapMusic.play();
        mapMusic.setVolume(0.1f);
        mapMusic.setLooping(true);

        // Pixel size
        tilePixelSize = 16 * scale;

        // Sprites
        spriteBatch = new SpriteBatch();
      
        // Pausing
        isPaused = false;
        pauseMenu = new Rectangle(100, 100, 200, 250);
        shapeRenderer = new ShapeRenderer();
        gdxThreadTasks.add(() -> textUtils = TextUtils.getInstance());

        // Battle
        this.gameStateManager = gameStateManager;
    }

    private void initializeCameraDrawing(GameData gameData){
        cam = gameData.getCamera();

        // Setting bounds for map
        MapProperties properties = tiledMap.getProperties();
        int mapWidth = properties.get("width", Integer.class);
        int mapHeight = properties.get("height", Integer.class);
        mapLeft = (cam.viewportWidth / 2f);
        mapRight = mapWidth * tilePixelSize - (cam.viewportWidth / 2f);
        mapBottom = 360;
        mapTop = mapBottom + mapHeight * tilePixelSize - (cam.viewportWidth / 2f) - 80;
        cam.position.set(mapRight /2f, mapTop / 2f, 0);

    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        while(!gdxThreadTasks.isEmpty()){
            gdxThreadTasks.poll().run();
        }
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }
    }


    @Override
    public void draw(GameData gameData) {
        if(cam == null)
            initializeCameraDrawing(gameData);
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
        for (Entity entity : world.getEntities()) {
            if (entity.getSpriteTexture() != null) {
                Texture sprite = entity.getSpriteTexture();

                spriteBatch.setProjectionMatrix(cam.combined);
                spriteBatch.begin();
                spriteBatch.draw(sprite, entity.getPosX(), entity.getPosY());
                spriteBatch.end();

                if (entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class) {
                    playerPosX = entity.getPosX();
                    playerPosY = entity.getPosY();
                    if(playerPosY > mapBottom && playerPosY < mapTop){
                        cam.position.set(cam.position.x, playerPosY, 0);

                        cam.update();
                    }
                    if (playerPosX > mapLeft && playerPosX < mapRight) {
                        cam.position.set(playerPosX, cam.position.y, 0);
                        cam.update();
                    }
                }
            }
        }
        tiledMapRenderer.getBatch().begin();
        tiledMapRenderer.renderTileLayer(overhangLayer);
        tiledMapRenderer.getBatch().end();
        if(isPaused) {
            //Drawing pause menu box
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.setColor(Color.WHITE);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            pauseMenu.setX(cam.position.x + cam.viewportWidth / 3f);
            pauseMenu.setY(cam.position.y - cam.viewportHeight / 8f);
            pauseMenu.draw(shapeRenderer, gameData.getDelta());
            shapeRenderer.end();

            //Drawing pause menu text
            spriteBatch.setProjectionMatrix(cam.combined);
            spriteBatch.begin();

            textUtils.drawNormalRoboto(
                    spriteBatch,
                    pauseMenuTitle,
                    Color.BLACK,
                    pauseMenu.getX() + 19,
                    pauseMenu.getY() + pauseMenu.getHeight() - 10);

            //Drawing options
            for (int i = 0; i < pauseActions.length; i++) {
                textUtils.drawSmallRoboto(spriteBatch, pauseActions[i], Color.BLACK, pauseMenu.getX() + 42, pauseMenu.getY() + (pauseMenu.getHeight() * 2 / 3f) - (i * 40));
            }

            spriteBatch.end();


            // Drawing selection triangle
            // Yoinked from BattleScene

            Gdx.gl.glEnable(GL20.GL_BLEND); //Allows for opacity
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);

            int triangleHeight = 20;
            int heightBetweenOptions = 20;
            int normalTextHeight = 24;
            int actionTopTextHeight = (int) (pauseMenu.getHeight() * 2 / 3f) + 40;
            int offsetFromActionHeadToFirstAction = 10;

            selectedOptionIndex = selectedOptionIndex % pauseActions.length;

            int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
            renderHeight = renderHeight + selectedOptionIndex * -heightBetweenOptions * 2;

            shapeRenderer.triangle(
                    pauseMenu.getX() + 15, pauseMenu.getY() + renderHeight,
                    pauseMenu.getX() + 30, pauseMenu.getY() + triangleHeight / 2f + renderHeight,
                    pauseMenu.getX() + 15, pauseMenu.getY() + triangleHeight + renderHeight
            );
            shapeRenderer.end();
        }
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if(isPaused){
            if(gameData.getKeys().isPressed(GameKeys.DOWN)) {
                if(selectedOptionIndex < pauseActions.length)
                    selectedOptionIndex++;
                else
                    selectedOptionIndex = 0;
            }
            if(gameData.getKeys().isPressed(GameKeys.UP)){
                if(selectedOptionIndex <= 0)
                    selectedOptionIndex = pauseActions.length-1;
                else
                    selectedOptionIndex--;
            }
            if(gameData.getKeys().isPressed(GameKeys.ESC)){
                isPaused = false;
                gameData.setPaused(isPaused);
            }
            if(gameData.getKeys().isPressed(GameKeys.ENTER)) {
                if (pauseActions[selectedOptionIndex].equals("Resume")) {
                    isPaused = false;
                    gameData.setPaused(isPaused);
                }
                if (pauseActions[selectedOptionIndex].equals("Inventory"))
                    System.out.println("Not implemented yet!");
                if (pauseActions[selectedOptionIndex].equals("Team")) {

                    for (Entity entity : world.getEntities()) {
                        if (entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class) {
                            MonsterTeamPart mtp = entity.getPart(MonsterTeamPart.class);
                            mtp.printMonsterTeamNames();
                        }
                    }
                }
                //System.out.println("You have no team");

                if(pauseActions[selectedOptionIndex].equals("Quit")){
                    isPaused = false;
                    gameData.setPaused(isPaused);
                    if(cam != null)
                        cam.position.set(gameData.getDisplayWidth()/2,gameData.getDisplayHeight()/2, 0);
                    gameStateManager.setDefaultState();
                }
            }
            return;
        }
        if(gameData.getKeys().isPressed(GameKeys.ESC)){
            isPaused = true;
            gameData.setPaused(isPaused);
        }
        if(gameData.getKeys().isPressed(GameKeys.E)){
            for(Entity entity: world.getEntities()){
                if(entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class){
                    playerMonsters = entity.getPart(MonsterTeamPart.class);
                    System.out.println("Added playermonsters");
                }
            }
            startEncounter(playerMonsters, playerMonsters);
        }
    }

    @Override
    public void dispose() {
        if(cam != null)
            cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2, 0);
        mapMusic.stop();
    }

    public void addEntityProcessingService (IEntityProcessingService eps){
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService (IEntityProcessingService eps){
        this.entityProcessorList.remove(eps);
    }

    public void addGamePluginService (IGamePluginService plugin){
        this.gamePluginList.add(plugin);
        gdxThreadTasks.add(() -> plugin.start(gameData, world));
    }

    public void removeGamePluginService (IGamePluginService plugin){
        this.gamePluginList.remove(plugin);
        gdxThreadTasks.add(() -> plugin.stop(gameData, world));
    }

    public void setBattleView(IBattleView battleView){ this.battleView = battleView; }

    public void removeBattleView(IBattleView battleView){ this.battleView = null; }

    @Override
    public float getMapLeft() {
        return mapLeft - (cam.viewportWidth/2f);
    }

    @Override
    public float getMapRight() {
        return mapRight + (cam.viewportWidth/2f);
    }

    @Override
    public float getMapBottom() {
        return mapBottom - (cam.viewportHeight/2f);
    }

    @Override
    public float getMapTop() {
        return mapTop + (cam.viewportHeight/2f);
    }

    @Override
    public int getTileSize() {
        return tilePixelSize;
    }

    @Override
    public boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer collsionLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
        TiledMapTileLayer.Cell cell = collsionLayer.getCell((int)Math.floor(x/tilePixelSize), (int) Math.floor(y/tilePixelSize));
        return cell.getTile().getProperties().containsKey("blocked");
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    public void startEncounter(MonsterTeamPart playerMonsters, MonsterTeamPart enemyMonsters){
        IBattleParticipant playerParticipant = playerMonsters.toBattleParticipant(true);
        IBattleParticipant enemyParticipant = enemyMonsters.toBattleParticipant(false);
        System.out.println(playerParticipant.getActiveMonster().getBackSprite());
        ((IGameViewService) battleView).init(gameStateManager);
        battleView.startBattle(playerParticipant, enemyParticipant, new IBattleCallback() {
            @Override
            public void onBattleEnd(IBattleResult result) {
                System.out.println("Somebody won!");
                gameStateManager.setState(MapView.this);
            }
        });
        gameStateManager.setState((IGameViewService) battleView);
    }
}