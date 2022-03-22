package dk.sdu.mmmi.modulemon;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.modulemon.managers.GameInputManager;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;
import org.osgi.framework.Bundle;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.FileHandler;

public class Game implements ApplicationListener {
    public static int WIDTH;
    public static int HEIGHT;
    private static World world = new World();
    public static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private GameStateManager gsm;
    private static List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    public Game(){
        init();
    }


    public void init(){
        //Testing java 1.8 stream()
//        List<String> test = new ArrayList<>();
//        test.add("lol");
//        boolean streamsWorking = test.stream().anyMatch(x->x.equalsIgnoreCase("lol"));
//        System.out.println("Streams working?: " + (streamsWorking ? "Yes" : "No!!"));

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Modulemon";
        cfg.width = 1000;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;
        //InputStream inStream = new java.io.BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("cat-icon.png"));
        System.out.println(getClass().getResource("/icons/cat-icon.png"));
        //Bundle bundle;
        System.out.println(getClass().getResource("/icons/cat-icon.png"));

        //File.createTempFile(getClass().getResource("/icons/cat-icon.png"))
        URL url2 = getClass().getResource("/icons/cat-icon.png");
        URL url = getClass().getResource("/icons/test.txt");
        //File configFile = new File(FileLocator.toFileURL(url).getPath());


        //cfg.addIcon(String.valueOf(getClass().getResource("/icons/cat-icon.png")), Files.FileType.Classpath);

        new LwjglApplication(this, cfg);
    }


    @Override
    public void create() {

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

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();

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

}
