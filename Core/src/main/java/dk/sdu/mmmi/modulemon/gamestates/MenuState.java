package dk.sdu.mmmi.modulemon.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.List;
import java.util.Objects;

public class MenuState implements IGameViewService {

    /**
     * Creates the necessary variables used for custom fonts.
     */
    private GlyphLayout glyphLayout;
    private SpriteBatch spriteBatch;
    private BitmapFont titleFont;
    private BitmapFont menuOptionsFont;
    private BitmapFont smallMenuFont;
    private Music menuMusic;

    private Texture logo;

    private String title = "";

    private int currentOption;
    private MenuStates currentMenuState = MenuStates.DEFAULT;
    private String[] menuOptions;
    private String[] defaultMenuOptions = new String[]{
            "Play",
            "Settings",
            "Quit"
    };


    GameData gameData = new GameData();

    public MenuState() {
    }

    @Override
    public void init(IGameStateManager gameStateManager) {
        menuMusic = Gdx.audio.newMusic(new OSGiFileHandle("/music/menu.ogg", MenuState.class));
        // Instantiates the variables
        spriteBatch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        menuMusic.play();
        menuMusic.setVolume(0.8f);
        menuMusic.setLooping(true);

        /*
          Sets up FontGenerator to enable us to use our own fonts.
         */
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
                new OSGiFileHandle("/fonts/Modulemon-Solid.ttf", this.getClass())
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        // Font size
        parameter.size = 80;

        // Sets the @titleFont to use our custom font file with the chosen font size
        titleFont = fontGenerator.generateFont(parameter);


        fontGenerator = new FreeTypeFontGenerator(
                new OSGiFileHandle("/fonts/Roboto-Medium.ttf", this.getClass()));
        // Font size
        parameter.size = 34;

        // Sets the @menuOptionsFont to use our custom font file with the chosen font size
        menuOptionsFont = fontGenerator.generateFont(parameter);

        parameter.size = 20;
        smallMenuFont = fontGenerator.generateFont(parameter);

        fontGenerator.dispose();

        logo = new Texture(new OSGiFileHandle("/icons/cat-logo.png", this.getClass()));

        // Sets the options for the menu
        menuOptions = defaultMenuOptions;
    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        if (currentMenuState == MenuStates.SELECTING_GAMESTATE) {
            title = "Select GaméstatE";
            List<IGameViewService> gameViews = Game.getGameViewServiceList();
            menuOptions = new String[gameViews.size() + 1];
            menuOptions[0] = "GO BACK";
            for (int i = 1; i <= gameViews.size(); i++) {
                menuOptions[i] = gameViews.get(i - 1).getClass().getName();
            }
        } else {
            //Default
            menuOptions = defaultMenuOptions;
            title = "ModulémoN";
        }
    }

    @Override
    public void draw(GameData _) {
        spriteBatch.setProjectionMatrix(Game.cam.combined);

        spriteBatch.begin();

        glyphLayout.setText(titleFont, title);

        spriteBatch.draw(logo, (Game.WIDTH) / 2.2f, (Game.HEIGHT - glyphLayout.height) / 1.2f);

        titleFont.setColor(Color.valueOf("ffcb05"));
        titleFont.draw(
                spriteBatch,
                title,
                (Game.WIDTH - glyphLayout.width) / 2,
                (Game.HEIGHT - glyphLayout.height) / 1.3f
        );

        /*
         * Runs through the entire menuOptions array and draws them all.
         * Also sets the currently selected option to a custom color
         */
        for (int i = 0; i < menuOptions.length; i++) {
            glyphLayout.setText(menuOptionsFont, menuOptions[i]);
            if (currentOption == i) menuOptionsFont.setColor(Color.valueOf("2a75bb"));
            else menuOptionsFont.setColor(Color.WHITE);
            menuOptionsFont.draw(
                    spriteBatch,
                    menuOptions[i],
                    (Game.WIDTH - glyphLayout.width) / 2f,
                    (Game.HEIGHT - 100 * i) / 2f
            );
        }

        glyphLayout.setText(smallMenuFont, "Press CTRL+K to open the Bundle Controller (if loaded)");
        smallMenuFont.draw(
                spriteBatch,
                "Press CTRL+K to open the Bundle Controller (if loaded)",
                (Game.WIDTH - glyphLayout.width) / 2f,
                40
        );

        spriteBatch.end();
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        // Moves up in the menu
        if (gameData.getKeys().isPressed(GameKeys.UP) || gameData.getKeys().isPressed(GameKeys.LEFT)) {
            if (currentOption > 0) {
                currentOption--;
            } else {
                currentOption = menuOptions.length - 1;
            }
        }
        // Moves down in the menu
        if (gameData.getKeys().isPressed(GameKeys.DOWN) || gameData.getKeys().isPressed(GameKeys.RIGHT)) {
            if (currentOption < menuOptions.length - 1) {
                currentOption++;
            } else {
                currentOption = 0;
            }
        }
        // Selects the current option
        if (gameData.getKeys().isPressed(GameKeys.ENTER) || gameData.getKeys().isPressed(GameKeys.E)) {
            selectOption(gameStateManager);
        }
    }

    /**
     * Handler for when an option is selected.
     * Based on what the currentOption is, it will execute the appertaining code.
     */
    private void selectOption(IGameStateManager gsm) {
        if (currentMenuState == MenuStates.SELECTING_GAMESTATE) {
            if (menuOptions[currentOption].equalsIgnoreCase("GO BACK")) {
                currentMenuState = MenuStates.DEFAULT;
                currentOption = 0;
                return;
            }

            List<IGameViewService> views = Game.getGameViewServiceList();
            if (currentOption > views.size()) {
                System.out.println("ERROR: Tried to set invalid view");
                currentOption = 0;
            }
            IGameViewService selectedView = views.get(currentOption - 1);
            gsm.setState(selectedView);
        } else {
            if (Objects.equals(menuOptions[currentOption], "Play")) {
                //gsm.setState(GameStateManager.PLAY);
                currentMenuState = MenuStates.SELECTING_GAMESTATE;
                currentOption = 0;
            }
            if (Objects.equals(menuOptions[currentOption], "Settings")) {
                System.out.println("No settings yet, but coming soon™!");
            }
            if (Objects.equals(menuOptions[currentOption], "Quit")) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        menuMusic.stop();
    }

    private enum MenuStates {
        DEFAULT,
        SELECTING_GAMESTATE,
        SETTINGS
    }
}
