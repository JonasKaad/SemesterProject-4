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
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.data.*;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.drawing.ImageDrawingUtils;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.ArrayList;
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
    private boolean showMonsterTeam;
    private boolean showTeamOptions;
    private boolean showSummary;
    private TextUtils textUtils;
    private ImageDrawingUtils imageDrawingUtils;
    private TextUtils textUtilsMonster;
    private Rectangle pauseMenu;
    private Rectangle monsterTeamMenu;
    private Rectangle teamActionMenu;
    private Rectangle summaryMenu;
    private String pauseMenuTitle = "GAME PAUSED";
    private String[] pauseActions = new String[]{"Resume", "Team", "Inventory", "Quit"};
    private String[] teamActions = new String[]{"Summary", "Switch", "Cancel"};
    private List<IMonster> monsterTeam = new ArrayList<>();
    private Rectangle[] monsterRectangles = new Rectangle[6];
    private int selectedOptionIndex = 0;
    private int selectedOptionIndexMonsterTeam = 0;
    private int teamOptionIndex = 0;
    private float mapLeft;
    private float mapRight;
    private float mapBottom;
    private float mapTop;
    private int tilePixelSize;

    private Color switchIndicatorColor = Color.BLACK;
    private boolean showSwitchingText = false;

    private MonsterTeamPart mtp;
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
        showMonsterTeam = false;
        showTeamOptions = false;
        summaryMenu = new Rectangle(100, 100, 380, 300);
        pauseMenu = new Rectangle(100, 100, 200, 250);
        monsterTeamMenu = new Rectangle(100, 100, 400, 550);
        teamActionMenu = new Rectangle(100, 100, 200, 200);
        for (int i = 0; i < monsterRectangles.length; i++) {
            Rectangle rect = new Rectangle(100, 100, 320, 70);
            monsterRectangles[i] = rect;
            //monsterRectangles[i] = new Rectangle(100, 100, 360, 100);
        }
        shapeRenderer = new ShapeRenderer();
        gdxThreadTasks.add(() -> textUtils = TextUtils.getInstance());
        //gdxThreadTasks.add(() -> textUtilsMonster = TextUtils.getInstance());
        gdxThreadTasks.add(() -> imageDrawingUtils = ImageDrawingUtils.getInstance());



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


        if(showTeamOptions) {
            //Drawing options menu box
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.setColor(Color.WHITE);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            teamActionMenu.setX(monsterTeamMenu.getX() + monsterTeamMenu.getWidth() + 20);
            teamActionMenu.setY(monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - teamActionMenu.getHeight());
            //teamActionMenu.setY(cam.position.y - cam.viewportHeight / 2.8f);
            teamActionMenu.draw(shapeRenderer, gameData.getDelta());
            shapeRenderer.end();

            //Drawing options menu text
            spriteBatch.setProjectionMatrix(cam.combined);
            spriteBatch.begin();

            textUtils.drawNormalRoboto(
                    spriteBatch,
                    "Choose Action",
                    Color.BLACK,
                    teamActionMenu.getX() + 19,
                    teamActionMenu.getY() + teamActionMenu.getHeight() - 10);

            //Drawing options
            for (int i = 0; i < teamActions.length; i++) {
                textUtils.drawSmallRoboto(spriteBatch, teamActions[i], Color.BLACK, teamActionMenu.getX() + 42, teamActionMenu.getY() + (teamActionMenu.getHeight() * 2 / 3f) - (i * 40));
            }

            spriteBatch.end();
        }
        if(showMonsterTeam) {
            for (Entity entity : world.getEntities()) {
                if (entity.getClass() == dk.sdu.mmmi.modulemon.Player.Player.class) {
                    mtp = entity.getPart(MonsterTeamPart.class);
                    monsterTeam = mtp.getMonsterTeam();

                    //Drawing monster menu box
                    shapeRenderer.setAutoShapeType(true);
                    shapeRenderer.setProjectionMatrix(cam.combined);
                    shapeRenderer.setColor(Color.WHITE);

                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                    monsterTeamMenu.setX(cam.position.x - cam.viewportWidth / 2.6f);
                    monsterTeamMenu.setY(cam.position.y - cam.viewportHeight / 3.2f);
                    monsterTeamMenu.draw(shapeRenderer, gameData.getDelta());
                    shapeRenderer.end();


                    //Drawing monster menu text
                    spriteBatch.setProjectionMatrix(cam.combined);
                    spriteBatch.begin();



                    textUtils.drawNormalRoboto(
                            spriteBatch,
                            "Your Team",
                            Color.BLACK,
                            monsterTeamMenu.getX() + 135,
                            monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - 10);

                    // Draws the text telling the player how to change order/switch monsters
                    if(showSwitchingText){
                        textUtils.drawSmallRoboto(
                                spriteBatch,
                                "Select two Monsters to switch their order",
                                Color.BLACK,
                                monsterTeamMenu.getX() + 50,
                                monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - 35);
                    }

                    //Drawing Names and HP

                    for (int i = 0; i < monsterTeam.size(); i++) {
                        //System.out.println(mtp.getMonsterTeam().get(i).getClass());
                        imageDrawingUtils.drawImage(spriteBatch, mtp.getMonsterTeam().get(i).getFrontSprite(), mtp.getMonsterTeam().get(i).getClass(), monsterTeamMenu.getX() + 42, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.6f) - (i * 80));
                        textUtils.drawSmallRoboto(spriteBatch, "Name: \t" + mtp.getMonsterTeam().get(i).getName(), Color.BLACK, monsterTeamMenu.getX() + 42+ 110, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.3f) - (i * (80)));
                        textUtils.drawSmallRoboto(spriteBatch, "HP: \t" + (mtp.getMonsterTeam().get(i).getHitPoints()), Color.BLACK, monsterTeamMenu.getX() + 42 + 110, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.45f) - (i * (80)));

                    }
                    spriteBatch.end();

                    /*
                    For some reason it needs two different for loops, otherwise it won't draw all the stuff.
                     */

                    // Drawing boxes around Monsters
                    shapeRenderer.begin();
                    for (int i = 0; i < monsterTeam.size(); i++) {
                        monsterRectangles[i].setX(monsterTeamMenu.getX() + 40);
                        monsterRectangles[i].setY(monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.6f) - (i * 80));
                        monsterRectangles[i].draw(shapeRenderer, gameData.getDelta());
                        Gdx.gl20.glLineWidth(2);
                    }
                    shapeRenderer.end();
                }
            }
        }


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

            if(showTeamOptions){
                Gdx.gl.glEnable(GL20.GL_BLEND); //Allows for opacity
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.BLACK);

                int triangleHeight = 20;
                int heightBetweenOptions = 20;
                int normalTextHeight = 24;
                int actionTopTextHeight = (int) (teamActionMenu.getHeight() * 2 / 3f) + 40;
                int offsetFromActionHeadToFirstAction = 10;

                teamOptionIndex = teamOptionIndex % teamActions.length;

                int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
                renderHeight = renderHeight + teamOptionIndex * -heightBetweenOptions * 2;

                shapeRenderer.triangle(
                        teamActionMenu.getX() + 15, teamActionMenu.getY() + renderHeight,
                        teamActionMenu.getX() + 30, teamActionMenu.getY() + triangleHeight / 2f + renderHeight,
                        teamActionMenu.getX() + 15, teamActionMenu.getY() + triangleHeight + renderHeight
                );
                shapeRenderer.end();
            }

            else if(showMonsterTeam){
                Gdx.gl.glEnable(GL20.GL_BLEND); //Allows for opacity
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(switchIndicatorColor);

                int triangleHeight = 20;
                int heightBetweenOptions = 40;
                int normalTextHeight = 24;
                int actionTopTextHeight = (int) (monsterTeamMenu.getHeight() * 2 / 2.6f) + 80;
                int offsetFromActionHeadToFirstAction = 8;

                selectedOptionIndexMonsterTeam = selectedOptionIndexMonsterTeam % monsterTeam.size();

                int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
                renderHeight = renderHeight + selectedOptionIndexMonsterTeam * -heightBetweenOptions * 2;

                shapeRenderer.triangle(
                        monsterTeamMenu.getX() + 15, monsterTeamMenu.getY() + renderHeight,
                        monsterTeamMenu.getX() + 30, monsterTeamMenu.getY() + triangleHeight / 2f + renderHeight,
                        monsterTeamMenu.getX() + 15, monsterTeamMenu.getY() + triangleHeight + renderHeight
                );
                shapeRenderer.end();
            }
            else {
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
    }

    int firstSelected = -1; // Default case = nothing selected
    int secondSelected = -1; // Default case = nothing selected
    int temporarySecondSelected = -1; // Default case = nothing selected
    boolean currentlySwitching = false;

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if(isPaused){
            if(gameData.getKeys().isPressed(GameKeys.DOWN)) {
                if(showTeamOptions){
                    if (teamOptionIndex < teamActions.length)
                        teamOptionIndex++;
                    else
                        teamOptionIndex = 0;
                }
                else if(showMonsterTeam){
                    if(firstSelected >= 0 && firstSelected != selectedOptionIndexMonsterTeam && temporarySecondSelected != -1){
                        // Resets the color back to black every time we go up or down the list
                        monsterRectangles[temporarySecondSelected].setBorderColor(Color.BLACK);
                    }
                    if(selectedOptionIndexMonsterTeam < monsterTeam.size()-1) {
                        selectedOptionIndexMonsterTeam++;
                    }
                    else {
                        selectedOptionIndexMonsterTeam = 0;
                    }
                    temporarySecondSelected = selectedOptionIndexMonsterTeam;
                    // If the first monster to be switched has been selected, and it's not equal to the one being hovered
                    if(firstSelected >= 0 && firstSelected != selectedOptionIndexMonsterTeam && temporarySecondSelected != -1){
                        // Color the currently hovered monster's border Cyan
                        monsterRectangles[temporarySecondSelected].setBorderColor(Color.valueOf("29d4ff"));
                    }
                }
                else {
                    if (selectedOptionIndex < pauseActions.length)
                        selectedOptionIndex++;
                    else
                        selectedOptionIndex = 0;
                }
            }
            if(gameData.getKeys().isPressed(GameKeys.UP)){
                if(showTeamOptions){
                    if (teamOptionIndex <= 0)
                        teamOptionIndex  = teamActions.length - 1;
                    else
                        teamOptionIndex--;
                }
                else if(showMonsterTeam){
                    if(firstSelected >= 0 && firstSelected != selectedOptionIndexMonsterTeam){
                        // Resets the color back to black every time we go up or down the list
                        monsterRectangles[temporarySecondSelected].setBorderColor(Color.BLACK);
                    }
                    if(selectedOptionIndexMonsterTeam <= 0)
                        selectedOptionIndexMonsterTeam = monsterTeam.size() -1;
                    else {
                        selectedOptionIndexMonsterTeam--;
                    }
                    temporarySecondSelected = selectedOptionIndexMonsterTeam;
                    // If the first monster to be switched has been selected, and it's not equal to the one being hovered
                    if(firstSelected >= 0 && firstSelected != selectedOptionIndexMonsterTeam){
                        // Color the currently hovered monster's border Cyan
                        monsterRectangles[temporarySecondSelected].setBorderColor(Color.valueOf("29d4ff"));
                    }
                }
                else {
                    if (selectedOptionIndex <= 0)
                        selectedOptionIndex = pauseActions.length - 1;
                    else
                        selectedOptionIndex--;
                }
            }
            if(gameData.getKeys().isPressed(GameKeys.ESC)){
                if(showTeamOptions){
                    showTeamOptions = false;
                    monsterRectangles[selectedOptionIndexMonsterTeam].setBorderColor(Color.BLACK);
                    teamOptionIndex = 0;
                }
                else if(showMonsterTeam){
                    showMonsterTeam = false;
                    resetMonsterTeamDrawing();
                    selectedOptionIndexMonsterTeam = 0;
                    pauseMenu.setFillColor(Color.WHITE);
                }
                else {
                    isPaused = false;
                    gameData.setPaused(isPaused);
                }
            }
            if(gameData.getKeys().isPressed(GameKeys.ENTER)) {
                if (showMonsterTeam) {
                    if(showTeamOptions) {
                        if (teamActions[teamOptionIndex].equals("Summary")) {

                            String summary = mtp.getMonsterTeam().get(selectedOptionIndexMonsterTeam).getName() + "\n" +
                                    mtp.getMonsterTeam().get(selectedOptionIndexMonsterTeam).getStats();
                            System.out.println("\nShowing Summary:");
                            System.out.println(summary);
                        }
                        if (teamActions[teamOptionIndex].equals("Switch")) {
                            showTeamOptions = false;
                            currentlySwitching = true;
                            if (firstSelected == -1) { // If nothing is selected
                                firstSelected = selectedOptionIndexMonsterTeam; // Select the current monster
                                switchIndicatorColor = new Color(Color.valueOf("ffcb05"));
                                monsterRectangles[firstSelected].setBorderColor(Color.valueOf("ffcb05"));
                                showSwitchingText = true;
                                System.out.println("Selected the first");
                                return;
                            }
                        }
                        if (teamActions[teamOptionIndex].equals("Cancel")) {
                            showTeamOptions = !showTeamOptions;
                            monsterRectangles[selectedOptionIndexMonsterTeam].setBorderColor(Color.BLACK);
                        }
                    }
                    else if (showSummary){

                    }

                    else if(currentlySwitching){
                        if (secondSelected == -1) {
                            secondSelected = selectedOptionIndexMonsterTeam; // Select the second current monster
                            System.out.println("Selected the second");
                        }
                        if (firstSelected == secondSelected){ // If the same monster has been chosen twice, reset
                            resetMonsterTeamDrawing();
                            System.out.println("Reset");
                            return;
                        }
                        if (firstSelected != -1 && secondSelected != -1) {
                            IMonster newFirstMonster = monsterTeam.get(secondSelected); // Switching the two Monsters.
                            IMonster newSecondMonster = monsterTeam.get(firstSelected);
                            System.out.println("Updated team");
                            monsterTeam.set(firstSelected, newFirstMonster);
                            monsterTeam.set(secondSelected, newSecondMonster);
                            resetMonsterTeamDrawing();
                            mtp.setMonsterTeam(monsterTeam); // Set the player's monster team to the new order
                            currentlySwitching = false;
                            teamOptionIndex = 0;
                        }
                    }

                    else {
                            showTeamOptions = true;
                            monsterRectangles[selectedOptionIndexMonsterTeam].setBorderColor(Color.valueOf("ffcb05"));
                    }

                }
                if(!showMonsterTeam) {
                    if (pauseActions[selectedOptionIndex].equals("Resume")) {
                        isPaused = false;
                        gameData.setPaused(isPaused);
                    }
                    if (pauseActions[selectedOptionIndex].equals("Team")) {
                        showMonsterTeam = true;
                        pauseMenu.setFillColor(Color.LIGHT_GRAY);
                    }
                    if (pauseActions[selectedOptionIndex].equals("Inventory"))
                        System.out.println("Not implemented yet!");


                    if (pauseActions[selectedOptionIndex].equals("Quit")) {
                        isPaused = false;
                        gameData.setPaused(isPaused);
                        if (cam != null)
                            cam.position.set(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2, 0);
                        gameStateManager.setDefaultState();
                    }
                }
            }
            return;
        }
        if(gameData.getKeys().isPressed(GameKeys.ESC)){
            isPaused = true;
            //currentlySwitching = false;
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

    /**
     * Resets all the drawing done to the Monster Team back to normal.
     * Sets the colors back to black and resets indexes.
     */
    private void resetMonsterTeamDrawing(){
        showSwitchingText = false;
        switchIndicatorColor = new Color(Color.BLACK); // Sets the triangle back to black
        // Sets the borders back to black
        if(firstSelected >= 0) {
            monsterRectangles[firstSelected].setBorderColor(Color.BLACK);
        }
        if(secondSelected >= 0){
            monsterRectangles[secondSelected].setBorderColor(Color.BLACK);
        }
        if(temporarySecondSelected >= 0) {
            monsterRectangles[temporarySecondSelected].setBorderColor(Color.BLACK);
        }
        // Resets the indexes
        firstSelected = -1;
        secondSelected = -1;
        currentlySwitching = false;
        teamOptionIndex = 0;
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

        gameStateManager.setState((IGameViewService) battleView);
        battleView.startBattle(playerParticipant, enemyParticipant, new IBattleCallback() {
            @Override
            public void onBattleEnd(IBattleResult result) {
                gameStateManager.setState(MapView.this);
            }
        });
    }
}