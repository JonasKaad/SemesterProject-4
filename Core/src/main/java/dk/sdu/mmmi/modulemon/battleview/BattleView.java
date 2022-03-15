package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleSceneOpenAnimation;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleViewAnimation;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.gamestates.GameState;
import dk.sdu.mmmi.modulemon.main.Game;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BattleView extends GameState implements IBattleView {
    private IBattleSimulation _battleSimulation;
    private BattleScene _battleScene;
    private Queue<BattleViewAnimation> blockingAnimations;
    private Queue<BattleViewAnimation> backgroundAnimations;


    /**
     * Creates the necessary variables used for custom fonts.
     */
    private GlyphLayout glyphLayout;
    private SpriteBatch spriteBatch;
    private BitmapFont menuOptionsFont;

    public BattleView(GameStateManager gsm) {
        super(gsm);
    }

    /**
     * Initialize for IBattleView
     */
    @Override
    public void init(IBattleParticipant player, IBattleParticipant enemy) {
        _battleSimulation = new MockBattleSimulation();
        _battleSimulation.StartBattle(player, enemy);
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();
        BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
        openingAnimation.start();
        blockingAnimations.add(openingAnimation);
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init() {
        spriteBatch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        _battleScene = new BattleScene();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Roboto-Medium.ttf"));

        // Font size
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;

        // Sets the @menuOptionsFont to use our custom font file with the chosen font size
        menuOptionsFont = fontGenerator.generateFont(parameter);
        fontGenerator.dispose();

        init(new MockPlayer(), new MockEnemy());
    }

    @Override
    public void update(float dt) {
        spriteBatch.setProjectionMatrix(Game.cam.combined);
        if (_battleSimulation == null) {
            spriteBatch.begin();
            glyphLayout.setText(menuOptionsFont, "Waiting for battle participants");
            menuOptionsFont.setColor(Color.WHITE);
            menuOptionsFont.draw(
                    spriteBatch,
                    "Waiting for battle participants",
                    (Game.WIDTH - glyphLayout.width) / 2f,
                    (Game.HEIGHT - glyphLayout.height) / 2f
            );
            spriteBatch.end();
            return;
        }

        //Is there any animations active?
        if (!blockingAnimations.isEmpty()) {
            BattleViewAnimation currentAnimation = blockingAnimations.peek();
            currentAnimation.update(dt);
            if (!currentAnimation.isRunning()) {
                //If the animation is done, then we remove the animation from the queue
                blockingAnimations.remove();
                BattleViewAnimation nextAnimation = blockingAnimations.peek();
                if (nextAnimation != null) {
                    nextAnimation.start();
                    ;
                }
            }

            _battleScene.draw();
            return; //The animation is blocking. We don't continue
        }


        _battleScene.draw();
    }

    @Override
    public void draw() {
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
