package dk.sdu.mmmi.modulemon.gamestates;

//import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.main.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.managers.GameKeys;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;

import java.util.Objects;

public class MenuState extends GameState{

    /**
     * Creates the necessary variables used for custom fonts.
     */
    private GlyphLayout glyphLayout;
    private SpriteBatch spriteBatch;
    private BitmapFont titleFont;
    private BitmapFont menuOptionsFont;

    private Texture logo;
    private Music menuMusic;

    private final String title = "ModulemoN";

    private int currentOption;
    private String[] menuOptions;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        // Instantiates the variables
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/lobby.ogg"));
        menuMusic.setLooping(true);
        menuMusic.play();
        spriteBatch = new SpriteBatch();
        glyphLayout = new GlyphLayout();

        /*
          Sets up FontGenerator to enable us to use our own fonts.
         */
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal("assets/fonts/Modulemon-Solid.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        // Font size
        parameter.size = 80;

        // Sets the @titleFont to use our custom font file with the chosen font size
        titleFont = fontGenerator.generateFont(parameter);


        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Roboto-Medium.ttf"));
        // Font size
        parameter.size = 34;

        // Sets the @menuOptionsFont to use our custom font file with the chosen font size
        menuOptionsFont = fontGenerator.generateFont(parameter);

        fontGenerator.dispose();

        logo = new Texture(Gdx.files.internal("assets/icons/cat-logo.png"));

        // Sets the options for the menu
        menuOptions = new String[] {
                "Play",
                "Settings",
                "Quit"
        };
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {
        spriteBatch.setProjectionMatrix(Game.cam.combined);

        spriteBatch.begin();

        glyphLayout.setText(titleFont, title);

        spriteBatch.draw(logo, (Game.WIDTH) / 2.2f, (Game.HEIGHT - glyphLayout.height) / 1.2f);

        titleFont.setColor(Color.valueOf("#ffcb05"));
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
            if (currentOption == i) menuOptionsFont.setColor(Color.valueOf("#2a75bb"));
            else menuOptionsFont.setColor(Color.WHITE);
            menuOptionsFont.draw(
                    spriteBatch,
                    menuOptions[i],
                    (Game.WIDTH - glyphLayout.width) / 2f,
                    (Game.HEIGHT - 100 * i ) / 2f
            );


        }

        spriteBatch.end();
    }

    @Override
    public void handleInput() {
        // Moves up in the menu
        if(GameKeys.isPressed(GameKeys.UP) || GameKeys.isPressed(GameKeys.LEFT)){
            if(currentOption > 0){
                currentOption--;
            }
            else{
                currentOption = menuOptions.length-1;
            }
        }
        // Moves down in the menu
        if(GameKeys.isPressed(GameKeys.DOWN) || GameKeys.isPressed(GameKeys.RIGHT)){
            if(currentOption < menuOptions.length-1){
                currentOption++;
            }
            else{
                currentOption = 0;
            }
        }
        // Selects the current option
        if(GameKeys.isPressed(GameKeys.ENTER) || GameKeys.isPressed(GameKeys.E)){
            selectOption();
        }

    }

    /**
     * Handler for when an option is selected.
     * Based on what the currentOption is, it will execute the appertaining code.
     */
    private void selectOption() {
        if(Objects.equals(menuOptions[currentOption], "Play")){
            gsm.setState(GameStateManager.BATTLE);
        }
        if(Objects.equals(menuOptions[currentOption], "Settings")){
            System.out.println("No settings yet, but coming soon™!");
        }
        if(Objects.equals(menuOptions[currentOption], "Quit")){
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        menuMusic.stop();
    }
}
