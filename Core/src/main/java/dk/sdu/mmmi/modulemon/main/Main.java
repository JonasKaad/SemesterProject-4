package dk.sdu.mmmi.modulemon.main;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Hello world!
 *
 */
public class Main
{

    public static void main( String[] args )
    {
        Game game = new Game();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Modulemon";
        cfg.width = 1000;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;
        cfg.addIcon("assets/icons/cat-icon.png", Files.FileType.Internal);

        new LwjglApplication(game, cfg);
    }
}
