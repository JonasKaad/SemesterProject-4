package dk.sdu.mmmi.modulemon.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleView;
import dk.sdu.mmmi.modulemon.Game;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.services.IGameSettings;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.ArrayList;
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
    private BitmapFont smallmenuOptionsFont;
    private BitmapFont settingsValueFont;
    private BitmapFont smallMenuFont;
    private Music menuMusic;

    private Texture logo;

    private String musicVolume = "%";
    private String soundVolume = "%";

    private String title = "";

    private int currentOption;
    private MenuStates currentMenuState = MenuStates.DEFAULT;
    private String[] menuOptions;
    private String[] defaultMenuOptions = new String[]{
            "Play",
            "Settings",
            "Quit"
    };
    private String[] settingOptions = new String[]{
            "Change Music Volume",
            "Change Sound Volume",
            "Use Persona Rectangles",
    };
    private List<String> settingsValueList = new ArrayList<>();
    private boolean showSettings = false;

    GameData gameData = new GameData();

    private IGameSettings settings;

    public MenuState(IGameSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(IGameStateManager gameStateManager) {
        menuMusic = AssetLoader.getInstance().getMusicAsset("/music/menu.ogg", MenuState.class);
        // Instantiates the variables
        spriteBatch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        menuMusic.play();
        menuMusic.setVolume(0);
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
        smallmenuOptionsFont = fontGenerator.generateFont(parameter);
        settingsValueFont = fontGenerator.generateFont(parameter);

        fontGenerator.dispose();

        logo = AssetLoader.getInstance().getTextureAsset("/icons/cat-logo.png", this.getClass());

        // Sets the options for the menu
        menuOptions = defaultMenuOptions;

        if(settings != null) {
            musicVolume = settings.getSetting("musicVolume") + "%";
            soundVolume = settings.getSetting("soundVolume") + "%";
            settingsValueList.add(musicVolume);
            settingsValueList.add(soundVolume);
            settingsValueList.add((Boolean) settings.getSetting("personaRectangles") ? "On" : "Off");
        }
    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        if (currentMenuState == MenuStates.SELECTING_GAMESTATE) {
            title = "Select GaméstatE";
            List<IGameViewService> gameViews = Game.getGameViewServiceList();
            menuOptions = new String[gameViews.size() + 1];

            for (int i = 0; i < gameViews.size(); i++) {
                menuOptions[i] = gameViews.get(i).toString();
            }
            menuOptions[menuOptions.length-1] = "GO BACK";
        }
        else if (currentMenuState == MenuStates.SETTINGS) {
            title = "Settings";
            menuOptions = new String[settingOptions.length+1];
            for (int i = 0; i < settingOptions.length; i++) {
                menuOptions[i] = settingOptions[i];
            }
            menuOptions[settingOptions.length] = "GO BACK";
        }
        else {
            //Default
            menuOptions = defaultMenuOptions;
            title = "ModulémoN";
        }
        // Sets the value of the volume option to the correct value from the settings.json file

        if(menuMusic.getVolume() !=  (int) settings.getSetting("musicVolume") / 100f){
            menuMusic.setVolume((int) settings.getSetting("musicVolume") / 100f);
            musicVolume = settings.getSetting("musicVolume") + "%";
            soundVolume = settings.getSetting("soundVolume") + "%";
            settingsValueList.set(0,musicVolume);
            settingsValueList.set(1,soundVolume);
        }
        // Sets the value of the Persona option to on/off depending on the value of the boolean.
        if(!Objects.equals(settingsValueList.get(2), (Boolean) settings.getSetting("personaRectangles") ? "On" : "Off")) {
            settingsValueList.set(2,(Boolean) settings.getSetting("personaRectangles") ? "On" : "Off");
        }
    }

    @Override
    public void draw(GameData _) {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        if(showSettings) {
            for (int i = 0; i < settingsValueList.size(); i++) {
                glyphLayout.setText(settingsValueFont, settingsValueList.get(i));
                settingsValueFont.draw(
                        spriteBatch,
                        settingsValueList.get(i),
                        (Game.WIDTH - glyphLayout.width) / 1.55f,
                        (Game.HEIGHT - 60 * (i)) / 2f
                );
            }
            for (int i = 0; i < menuOptions.length; i++) {
                glyphLayout.setText(smallmenuOptionsFont, menuOptions[i]);
                if (currentOption == i) smallmenuOptionsFont.setColor(Color.valueOf("2a75bb"));
                else smallmenuOptionsFont.setColor(Color.WHITE);
                smallmenuOptionsFont.draw(
                        spriteBatch,
                        menuOptions[i],
                        (Game.WIDTH - glyphLayout.width) / 2f,
                        (Game.HEIGHT - 60 * i) / 2f
                );
            }
        }
        else {
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
        if (gameData.getKeys().isPressed(GameKeys.UP)) {
            if (currentOption > 0) {
                currentOption--;
            } else {
                currentOption = menuOptions.length - 1;
            }
        }
        // Moves down in the menu
        if (gameData.getKeys().isPressed(GameKeys.DOWN)) {
            if (currentOption < menuOptions.length - 1) {
                currentOption++;
            } else {
                currentOption = 0;
            }
        }

        if(gameData.getKeys().isPressed(GameKeys.LEFT)){
            handleSettings("LEFT");
        }
        if(gameData.getKeys().isPressed(GameKeys.RIGHT)){
            handleSettings("RIGHT");
        }
        // Selects the current option
        if (gameData.getKeys().isPressed(GameKeys.ACTION) || gameData.getKeys().isPressed(GameKeys.E)) {
            handleSettings("ACTION");
            selectOption(gameStateManager);
        }
    }

    /**
     * Handler for when an option is selected.
     * Based on what the currentOption is, it will execute the appertaining code.
     */
    private void selectOption(IGameStateManager gsm) {
        if (menuOptions[currentOption].equalsIgnoreCase("GO BACK")) {
            currentMenuState = MenuStates.DEFAULT;
            currentOption = 0;
            showSettings = false;
            return;
        }


        if (currentMenuState == MenuStates.SELECTING_GAMESTATE) {
            List<IGameViewService> views = Game.getGameViewServiceList();
            if (currentOption > views.size()) {
                System.out.println("ERROR: Tried to set invalid view");
                currentOption = 0;
            }
            IGameViewService selectedView = views.get(currentOption );
            gsm.setState(selectedView);
            if(selectedView instanceof IBattleView){
                ((IBattleView)selectedView).startBattle(null, null, null);
            }
        } else {
            if (Objects.equals(menuOptions[currentOption], "Play")) {
                //gsm.setState(GameStateManager.PLAY);
                currentMenuState = MenuStates.SELECTING_GAMESTATE;
                currentOption = 0;
            }
            if (Objects.equals(menuOptions[currentOption], "Settings")) {
                currentMenuState = MenuStates.SETTINGS;
                showSettings = true;
            }
            if (Objects.equals(menuOptions[currentOption], "Quit")) {
                Gdx.app.exit();
            }
        }
    }

    /**
     * Method to handle what logic should happen in the Settings menu. It checks the currently selected/hovered setting
     * and executes the code relevant to that. It takes the parameter keyInput.
     * @param keyInput declares what kind of key was pressed such that relevant logic can be applied. For instance, if you
     *                want to increase something when pressing right and decrease when pressing left.
     */
    private void handleSettings(String keyInput){
        switch(keyInput){
            case "RIGHT": {
                if (menuOptions[currentOption].equalsIgnoreCase("Change Music Volume")) {
                    if (!((int) settings.getSetting("musicVolume") >= 100)) {
                        int new_volume = (int) settings.getSetting("musicVolume") + 10;
                        settings.setSetting("musicVolume", new_volume);

                        musicVolume = (int) settings.getSetting("musicVolume") + "%";
                        settingsValueList.set(0, musicVolume);
                    }
                    break;
                }

                if (menuOptions[currentOption].equalsIgnoreCase("Change Sound Volume")) {
                    if (!((int) settings.getSetting("soundVolume") >= 100)) {
                        int new_volume = (int) settings.getSetting("soundVolume") + 10;
                        settings.setSetting("soundVolume", new_volume);

                        soundVolume = (int) settings.getSetting("soundVolume") + "%";
                        settingsValueList.set(1, soundVolume);
                    }
                    break;
                }
                boolean_settings_switch_on_off();
                break;
            }
            case "LEFT":
            {
                if (menuOptions[currentOption].equalsIgnoreCase("Change Music Volume")) {
                    if (((int) settings.getSetting("musicVolume") > 0)) {
                        int new_volume = (int) settings.getSetting("musicVolume") - 10;
                        settings.setSetting("musicVolume", new_volume);

                        musicVolume = (int) settings.getSetting("musicVolume") + "%";
                        settingsValueList.set(0, musicVolume);
                    }
                    break;
                }

                if (menuOptions[currentOption].equalsIgnoreCase("Change Sound Volume")) {
                    if (((int) settings.getSetting("soundVolume") > 0)) {
                        int new_volume = (int) settings.getSetting("soundVolume") - 10;
                        settings.setSetting("soundVolume", new_volume);

                        soundVolume = (int) settings.getSetting("soundVolume") + "%";
                        settingsValueList.set(1, soundVolume);
                    }
                    break;
                }
                boolean_settings_switch_on_off();
                break;
            }
            case "ACTION":
            {
                boolean_settings_switch_on_off();
            }
        }
    }

    /**
     * Method to change the value of a boolean Setting. Switches between true and false which changes the value
     * displayed on screen to either "On" or "Off".
     */
    private void boolean_settings_switch_on_off() {
        // Checks if the currently selected/hovered setting is the option for Persona Rectangles
        if (menuOptions[currentOption].equalsIgnoreCase("Use Persona Rectangles")) {
            if(! ((Boolean) settings.getSetting("personaRectangles"))){
                settingsValueList.set(2, "On");
                settings.setSetting("personaRectangles", true);
                return;
            }
            else{
                settingsValueList.set(2, "Off");
                settings.setSetting("personaRectangles", false);
                return;
            }
        }
    }


    @Override
    public void dispose() {
        menuMusic.stop();
        menuMusic.dispose();
        menuMusic = null;
    }

    private enum MenuStates {
        DEFAULT,
        SELECTING_GAMESTATE,
        SETTINGS
    }
}
