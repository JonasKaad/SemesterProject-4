package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
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
    private Music _battleMusic;
    private MenuState menuState = MenuState.DEFAULT;
    private Queue<BattleViewAnimation> blockingAnimations;
    private Queue<BattleViewAnimation> backgroundAnimations;

    private String[] defaultActions = new String[]{"Fight", "Switch", "Animate", "Quit"};
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
        _battleMusic.play();
        _battleMusic.setLooping(true);
        menuState = MenuState.DEFAULT;

        BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
        openingAnimation.start();
        blockingAnimations.add(openingAnimation);
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init() {
        _battleMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/battle_music.ogg"));
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
            return;
        }
    }

    @Override
    public void draw() {
        _battleScene.draw();
    }


    @Override
    public void handleInput() {
        if(!blockingAnimations.isEmpty()){
            //If any blocking animations, don't allow any input.
            _battleScene.setActionBoxAlpha(0.5f);
            return;
        }
        _battleScene.setActionBoxAlpha(1f);

        if(menuState == MenuState.DEFAULT) {
            _battleScene.setTextToDisplay("Choose an action!");
            _battleScene.setActionTitle("Your actions:");
            _battleScene.setActions(this.defaultActions);

            if (GameKeys.isPressed(GameKeys.ENTER)) {
                String selectedAction = defaultActions[this.selectedAction % defaultActions.length];
                _battleScene.setTextToDisplay("You have chosen: " + selectedAction);
                if (selectedAction.equalsIgnoreCase("quit")) {
                    System.out.println("Bye!!");
                    Gdx.app.exit();
                } else if (selectedAction.equalsIgnoreCase("Animate")) {
                    BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
                    openingAnimation.start();
                    blockingAnimations.add(openingAnimation);
                } else if(selectedAction.equalsIgnoreCase("Fight")){
                    this.menuState = MenuState.FIGHT;
                }
            }
        } else if(menuState == MenuState.FIGHT){
            _battleScene.setActionTitle("Moves:");
            IMonster playerMonster = _battleSimulation.getPlayer().getActiveMonster();

            Object[] monsterMoves = new Object[playerMonster.getMoves().size()+1];
            monsterMoves[0] = "Cancel";
            for(int i = 1; i <= playerMonster.getMoves().size(); i++){
                monsterMoves[i] = playerMonster.getMoves().get(i-1);
            }
            _battleScene.setActions(monsterMoves);


            //Get selected move
            Object selectedAction = monsterMoves[this.selectedAction % monsterMoves.length];
            if(selectedAction instanceof String){
                _battleScene.setTextToDisplay("Go back");
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.DEFAULT;
                }
            }else if(selectedAction instanceof IMonsterMove){
                IMonsterMove move = ((IMonsterMove) selectedAction);
                _battleScene.setTextToDisplay("Move: ["+move.getType()+"] " + move.getName() + ". Deals damage: " + move.getDamage());
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    System.out.println("I'm supposed to try and use the move: " + move.getName());
                    this.menuState = MenuState.DEFAULT;
                    this.selectedAction = 0;
                }
            }
        }


        //Handle up/down for choosing actions
        Object[] currentlyShownActions = this._battleScene.getActions();
        if(selectedAction > currentlyShownActions.length-1){
            selectedAction = 0;
        }

        if(currentlyShownActions.length > 0){
            if (GameKeys.isPressed(GameKeys.DOWN)) {
                if (selectedAction < currentlyShownActions.length - 1) {
                    selectedAction++;
                } else {
                    selectedAction = 0;
                }
                this._battleScene.setSelectedActionIndex(selectedAction);
            }
            if (GameKeys.isPressed(GameKeys.UP)) {
                if (selectedAction > 0) {
                    selectedAction--;
                } else {
                    selectedAction = currentlyShownActions.length - 1;
                }
                this._battleScene.setSelectedActionIndex(selectedAction);
            }
        }
    }

    @Override
    public void dispose() {
        _battleMusic.stop();
    }
}
