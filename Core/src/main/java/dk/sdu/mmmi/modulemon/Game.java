package dk.sdu.mmmi.modulemon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.services.*;
import dk.sdu.mmmi.modulemon.managers.GameInputManager;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;
import org.lwjgl.opengl.Display;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {
    public static int WIDTH;
    public static int HEIGHT;
    private static World world = new World();
    public static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private static GameStateManager gsm;
    private static List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IGameViewService> gameViewServiceList = new CopyOnWriteArrayList<>();
    private static IBundleControllerService bundleControllerService;
    private static Queue<Runnable> gdxThreadTasks = new LinkedList<>();

    public Game(){
        init();
    }


    public void init(){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Modulémon";
        cfg.width = 1280;
        cfg.height = 720;
        cfg.useGL30 = false;
        cfg.resizable = true;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        //Line below doesn't work yet, but just let it be - Alexander
        Display.setIcon(hackIcon("/icons/cat-icon.png"));
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.setToOrtho(false, WIDTH, HEIGHT); // does the same as cam.translate()
        cam.update();
        Gdx.input.setInputProcessor(
                new GameInputManager(gameData)
        );

        gsm = new GameStateManager();
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        gameData.setDelta(Gdx.graphics.getDeltaTime());

        //Run tasks on the LibGDX thread for OSGi
        while(!gdxThreadTasks.isEmpty()){
            gdxThreadTasks.poll().run();
        }

        gsm.update(gameData);
        gsm.draw(gameData);

        gameData.getKeys().update();

        update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }

        if(gameData.getKeys().isDown(GameKeys.K)
                && gameData.getKeys().isDown(GameKeys.LEFT_CTRL)
                && bundleControllerService != null){
            Game.bundleControllerService.openController();
        }
    }


    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}

    public void addEntityProcessingService(IEntityProcessingService entityProcessingService) {
        entityProcessorList.add(entityProcessingService);
    }

    public void removeEntityProcessingService(IEntityProcessingService entityProcessingService) {
        entityProcessorList.remove(entityProcessingService);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService postEntityProcessingService) {
        postEntityProcessorList.add(postEntityProcessingService);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService postEntityProcessingService) {
        postEntityProcessorList.remove(postEntityProcessingService);
    }

    // Adds a game plugin service to the game (Calls the plugin's start method)
    public void addGamePluginService(IGamePluginService gamePluginService) {
        gamePluginList.add(gamePluginService);
        gamePluginService.start(gameData, world);

    }

    // Removes a game plugin service to the game (Calls the plugin's stop method)
    public void removeGamePluginService(IGamePluginService gamePluginService) {
        gamePluginList.remove(gamePluginService);
        gamePluginService.stop(gameData, world);
    }

    public void addGameViewServiceList(IGameViewService gameViewService){
        System.out.println("WOOT, GAMEVIEWSERVICE LOADED: " + gameViewService.getClass().getName());
        gameViewServiceList.add(gameViewService);
    }

    public void removeGameViewServiceList(IGameViewService gameViewService){
        if(gsm.getCurrentGameState().equals(gameViewService)){
            gdxThreadTasks.add(() -> gsm.setDefaultState());
            System.out.println("Threw player out of scene because it was unloaded!");
        }
        gameViewServiceList.remove(gameViewService);
    }

    public static List<IGameViewService> getGameViewServiceList() {
        return gameViewServiceList;
    }

    public void addBundleController(IBundleControllerService bundleControllerService){
        System.out.println("A bundle controller was injected.");
        Game.bundleControllerService = bundleControllerService;
    }

    public void removeBundleController(IBundleControllerService bundleControllerService){
        Game.bundleControllerService = null;
    }

    private ByteBuffer[] hackIcon(String resourceName){
        ByteBuffer[] byteBuffer = new ByteBuffer[1];
        Pixmap pixmap = new Pixmap(new OSGiFileHandle(resourceName));
        if (pixmap.getFormat() != Pixmap.Format.RGBA8888) {
            Pixmap rgba = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
            rgba.drawPixmap(pixmap, 0, 0);
            pixmap = rgba;
        }

        byteBuffer[0] = ByteBuffer.allocateDirect(pixmap.getPixels().limit());
        byteBuffer[0].put(pixmap.getPixels()).flip();
        pixmap.dispose();
        return byteBuffer;
    }
}
