package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleSceneOpenAnimation;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleViewAnimation;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.gamestates.GameState;
import dk.sdu.mmmi.modulemon.main.Game;
import dk.sdu.mmmi.modulemon.managers.GameKeys;
import dk.sdu.mmmi.modulemon.managers.GameStateManager;

import java.util.LinkedList;
import java.util.Queue;

public class BattleView extends GameState implements IBattleView {
    private IBattleSimulation _battleSimulation;
    private BattleScene _battleScene;
    private Sound _battleMusic;
    private long bgm_loopingId;
    private Queue<BattleViewAnimation> blockingAnimations;
    private Queue<BattleViewAnimation> backgroundAnimations;

    private String[] actions = new String[]{"Fight", "Switch", "Quit"};
    private int selectedAction = 0;


    /**
     * Creates the necessary variables used for custom fonts.
     */
    private SpriteBatch spriteBatch;

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
        bgm_loopingId = _battleMusic.loop();

        BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
        openingAnimation.start();
        blockingAnimations.add(openingAnimation);
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init() {
        _battleMusic = Gdx.audio.newSound(Gdx.files.internal("assets/music/battle_music.mp3"));
        spriteBatch = new SpriteBatch();
        _battleScene = new BattleScene();
        init(new MockPlayer(), new MockEnemy());
    }

    @Override
    public void update(float dt) {
        spriteBatch.setProjectionMatrix(Game.cam.combined);
        if (_battleSimulation == null) {
            spriteBatch.begin();
            TextUtils.getInstance().drawBigRoboto(spriteBatch, "Waiting for battle participants", Color.WHITE, 100, Game.HEIGHT / 2f);
            spriteBatch.end();
            return;
        }

        //Is there any animations active?
        if (!blockingAnimations.isEmpty()) {
            BattleViewAnimation currentAnimation = blockingAnimations.peek();
            currentAnimation.update(dt);
            if (!currentAnimation.isRunning()) {
                //If the animation is done, then we remove the animation from the queue
                System.out.println("Animation is done");
                blockingAnimations.remove();
                BattleViewAnimation nextAnimation = blockingAnimations.peek();
                if (nextAnimation != null) {
                    nextAnimation.start();
                }
            }

            _battleScene.draw();
            return; //The animation is blocking. We don't continue
        }

        //Take input
        _battleScene.setTextToDisplay("Choose an action!");
        _battleScene.setActions(this.actions);
        if (GameKeys.isPressed(GameKeys.DOWN)) {
            if(selectedAction < actions.length-1){
                selectedAction++;
            }
            else{
                selectedAction = 0;
            }
            this._battleScene.setSelectedActionIndex(selectedAction);
        }
        if (GameKeys.isPressed(GameKeys.UP)) {
            if(selectedAction > 0){
                selectedAction--;
            }
            else{
                selectedAction = actions.length-1;
            }
            this._battleScene.setSelectedActionIndex(selectedAction);
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            String selectedAction = actions[this.selectedAction % actions.length];
            System.out.println("DU HAR VALGT: " + selectedAction);
            _battleScene.setTextToDisplay("You have chosen: " + selectedAction);
            if (selectedAction.equalsIgnoreCase("quit")) {
                System.out.println("Bye!!");
                Gdx.app.exit();
            }
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
        _battleMusic.stop(bgm_loopingId);
    }
}
