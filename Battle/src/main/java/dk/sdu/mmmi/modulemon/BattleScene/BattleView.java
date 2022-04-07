package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.BattleScene.animations.*;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleSceneMock.BattleParticipantMocks;
import dk.sdu.mmmi.modulemon.CommonBattle.*;
import dk.sdu.mmmi.modulemon.CommonBattle.BattleEvents.*;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.drawing.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.drawing.PersonaRectangle;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.LinkedList;
import java.util.Queue;


public class BattleView implements IGameViewService, IBattleView {

    private boolean _isInitialized;
    private IBattleCallback _battleCallback;
    private IBattleSimulation _battleSimulation;
    private BattleScene _battleScene;
    private Music _battleMusic;
    private Sound _attackSound;
    private Sound _winSound;
    private MenuState menuState = MenuState.DEFAULT;
    private Queue<BaseAnimation> blockingAnimations;
    private Queue<BaseAnimation> backgroundAnimations;

    private String[] defaultActions;
    private int selectedAction = 0;


    /**
     * Creates the necessary variables used for custom fonts.
     */
    private SpriteBatch spriteBatch;

    public BattleView() {
        System.out.println("BATTLE VIEW BEING CONSTRUCTED!!!");
        _isInitialized = false;
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();
        menuState = MenuState.DEFAULT;

        defaultActions = new String[]{"Fight", "Switch", "Animate", "Style", "Quit"};
    }

    /**
     * Initialize for IBattleView
     */
    public void startBattle(IBattleParticipant player, IBattleParticipant enemy, IBattleCallback callback) {
        _battleMusic = Gdx.audio.newMusic(new OSGiFileHandle("/music/battle_music.ogg", this.getClass()));
        _attackSound = Gdx.audio.newSound(new OSGiFileHandle("/sounds/slam.ogg", this.getClass()));
        _winSound = Gdx.audio.newSound(new OSGiFileHandle("/sounds/you_won.ogg", this.getClass()));
        _battleSimulation.StartBattle(player, enemy);
        _battleCallback = callback;
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();
        _battleMusic.play();
        _battleMusic.setVolume(0.1f);
        _battleMusic.setLooping(true);
        menuState = MenuState.DEFAULT;
        _battleScene.setActionTitle("Your actions:");
        _battleScene.setActions(this.defaultActions);

        BaseAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
        blockingAnimations.add(openingAnimation);
    }

    @Override
    public IGameViewService getGameView() {
        return this;
    }

    @Override
    public void forceBattleEnd() {
        handleBattleEnd(new VictoryBattleEvent("The battle has ended prematurely", _battleSimulation.getState().getPlayer()));
    }

    public void handleBattleEnd(VictoryBattleEvent victoryBattleEvent){
        if(_battleCallback != null){
            _battleCallback.onBattleEnd(new BattleResult(victoryBattleEvent.getWinner(), _battleSimulation.getState().getPlayer(), _battleSimulation.getState().getEnemy()));
        }
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init() {
        spriteBatch = new SpriteBatch();
        _battleScene = new BattleScene();

        _isInitialized = true;
        //Temp
        if(_battleSimulation != null)
            startBattle(BattleParticipantMocks.getPlayer(), BattleParticipantMocks.getOpponent(), null);

    }

    //OSGi dependency injection
    public void setBattleSimulation(IBattleSimulation battleSimulation) {
        this._battleSimulation = battleSimulation;
    }

    public void removeBattleSimulation(IBattleSimulation battleSimulation) {
        this._battleSimulation = null;
    }

    public void setBattleScene(BattleScene battleScene) {
        this._battleScene = battleScene;
    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        //spriteBatch.setProjectionMatrix(Game.cam.combined);
        if (!_isInitialized) {
            return;
        }
        if (_battleSimulation == null) {
            spriteBatch.begin();
            TextUtils.getInstance().drawBigRoboto(spriteBatch, "Waiting for battle participants", Color.WHITE, 100, gameData.getDisplayHeight() / 2f);
            spriteBatch.end();
            return;
        }

        //Is there any animations active?
        if (!blockingAnimations.isEmpty()) {
            //Set in update() because some animations depend on it.
            if (_battleScene != null) {
                _battleScene.setGameHeight(gameData.getDisplayHeight());
                _battleScene.setGameWidth(gameData.getDisplayWidth());
            }

            BaseAnimation currentAnimation = blockingAnimations.peek();
            if(!currentAnimation.isStarted()){
                currentAnimation.start();
            }

            currentAnimation.update(gameData);
            if (currentAnimation.isFinished()) {
                //If the animation is done, then we remove the animation from the queue
                currentAnimation.runEventDoneIfSet();
                blockingAnimations.remove();
                BaseAnimation nextAnimation = blockingAnimations.peek();
                if (nextAnimation != null) {
                    nextAnimation.start();
                    return;
                }
            } else {
                return; //Current animation still working, so just return
            }
        }

        //Check for events
        IBattleEvent battleEvent = _battleSimulation.getNextBattleEvent();
        if (battleEvent != null) {
            if (battleEvent instanceof MoveBattleEvent) {
                MoveBattleEvent event = (MoveBattleEvent) battleEvent;
                if (event.getUsingParticipant().isPlayerControlled()) {
                    //Player attacked
                    PlayerBattleAttackAnimation battleAnimation = new PlayerBattleAttackAnimation(_battleScene, _attackSound);
                    battleAnimation.setOnEventDone(() -> {
                        addEmptyAnimation(1000, true);
                        _battleScene.setTextToDisplay("...");
                    });
                    battleAnimation.start();
                    blockingAnimations.add(battleAnimation);
                    _battleScene.setHealthIndicatorText(String.format("-%d HP", event.getDamage()));
                } else {
                    //Enemy attacked
                    EnemyBattleAttackAnimation battleAnimation = new EnemyBattleAttackAnimation(_battleScene, _attackSound);
                    battleAnimation.start();
                    blockingAnimations.add(battleAnimation);
                    _battleScene.setHealthIndicatorText(String.format("-%d HP", event.getDamage()));
                }

                this._battleScene.setTextToDisplay(event.getText());
            } else if (battleEvent instanceof InfoBattleEvent) {
                addEmptyAnimation(2000, true);
                this._battleScene.setTextToDisplay(battleEvent.getText());
            } else if (battleEvent instanceof ChangeMonsterBattleEvent) {
                ChangeMonsterBattleEvent event = (ChangeMonsterBattleEvent) battleEvent;
                if (event.getParticipant().isPlayerControlled()) {
                    //There is no player enemy animation. Just a still frame for now
                    addEmptyAnimation(2000, true);
                    this._battleScene.setTextToDisplay(battleEvent.getText());
                } else {
                    EnemyDieAnimation enemyDieAnimation = new EnemyDieAnimation(_battleScene);
                    enemyDieAnimation.start();
                    enemyDieAnimation.setOnEventDone(() -> _battleScene.resetPositions());
                    blockingAnimations.add(enemyDieAnimation);

                    EmptyAnimation emptyAnimation = new EmptyAnimation(2000);
                    blockingAnimations.add(emptyAnimation);
                    _battleScene.setTextToDisplay(battleEvent.getText());
                }
            } else if (battleEvent instanceof VictoryBattleEvent) {
                EnemyDieAnimation enemyDieAnimation = new EnemyDieAnimation(_battleScene);
                enemyDieAnimation.setOnEventDone(() -> {
                    handleBattleEnd((VictoryBattleEvent) battleEvent);
                    //Should be removed later:
                    gameStateManager.setDefaultState();
                });
                enemyDieAnimation.start();
                blockingAnimations.add(enemyDieAnimation);
                this._winSound.play();
                this._battleScene.setTextToDisplay(battleEvent.getText());
            }
        }
    }

    @Override
    public void draw(GameData gameData) {
        //Game data
        _battleScene.setGameHeight(gameData.getDisplayHeight());
        _battleScene.setGameWidth(gameData.getDisplayWidth());

        //Update information
        if(_battleSimulation != null) {
            IMonster playerActiveMonster = _battleSimulation.getState().getPlayer().getActiveMonster();
            _battleScene.setPlayerSprite(playerActiveMonster.getBackSprite(), playerActiveMonster.getClass());
            _battleScene.setPlayerMonsterName(playerActiveMonster.getName());
            _battleScene.setPlayerHP(Integer.toString(playerActiveMonster.getHitPoints()));

            IMonster enemyActiveMonster = _battleSimulation.getState().getEnemy().getActiveMonster();
            _battleScene.setEnemySprite(enemyActiveMonster.getFrontSprite(), enemyActiveMonster.getClass());
            _battleScene.setEnemyMonsterName(enemyActiveMonster.getName());
            _battleScene.setEnemyHP(Integer.toString(enemyActiveMonster.getHitPoints()));
         }

        _battleScene.setSelectedActionIndex(selectedAction);
        _battleScene.draw(gameData.getDelta());
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if (!blockingAnimations.isEmpty() || !_isInitialized) {
            //If any blocking animations, don't allow any input.
            _battleScene.setActionBoxAlpha(0.5f);
            return;
        }
        _battleScene.setActionBoxAlpha(1f);
        GameKeys keys = gameData.getKeys();

        if (menuState == MenuState.DEFAULT) {
            _battleScene.setActionTitle("Your actions:");
            _battleScene.setActions(this.defaultActions);

            String selectedAction = defaultActions[this.selectedAction % defaultActions.length];
            if (selectedAction.equalsIgnoreCase("Fight")) {
                _battleScene.setTextToDisplay("Choose a move to damage your opponent");
                if (keys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.FIGHT;
                }
            } else if (selectedAction.equalsIgnoreCase("Switch")) {
                _battleScene.setTextToDisplay("[Not implemented] Change your active monster");
                if (keys.isPressed(GameKeys.ENTER)) {
                    System.out.println("Switching monster isn't implemented yet");
                }
            } else if (selectedAction.equalsIgnoreCase("Animate")) {
                _battleScene.setTextToDisplay("Show a fancy pancy battle-animation");
                if (keys.isPressed(GameKeys.ENTER)) {
                    System.out.println("Switching monster isn't implemented yet");
                    BaseAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
                    openingAnimation.start();
                    blockingAnimations.add(openingAnimation);
                }
            }else if(selectedAction.equalsIgnoreCase("Style")){
                _battleScene.setTextToDisplay("Change box-styles");
                if(keys.isPressed(GameKeys.ENTER)){
                    if(_battleScene.getPlayerBoxRect() instanceof PersonaRectangle) {
                        _battleScene.setPlayerBoxRectStyle(Rectangle.class);
                        _battleScene.setEnemyBoxRectStyle(Rectangle.class);
                        _battleScene.setActionBoxRectStyle(Rectangle.class);
                        _battleScene.setTextBoxRectStyle(Rectangle.class);
                    }else {
                        _battleScene.setPlayerBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setEnemyBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setActionBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setTextBoxRectStyle(PersonaRectangle.class);
                    }
                }
            } else if (selectedAction.equalsIgnoreCase("Quit")) {
                _battleScene.setTextToDisplay("Ends the battle");
                if (keys.isPressed(GameKeys.ENTER)) {
                    gameStateManager.setDefaultState();
                }
            }
        } else if (menuState == MenuState.FIGHT) {
            _battleScene.setActionTitle("Moves:");
            IMonster playerMonster = _battleSimulation.getState().getPlayer().getActiveMonster();

            Object[] monsterMoves = new Object[playerMonster.getMoves().size() + 1];
            monsterMoves[monsterMoves.length - 1] = "Cancel";
            for (int i = 0; i < playerMonster.getMoves().size(); i++) {
                monsterMoves[i] = playerMonster.getMoves().get(i);
            }
            _battleScene.setActions(monsterMoves);


            //Get selected move
            Object selectedAction = monsterMoves[this.selectedAction % monsterMoves.length];
            if (selectedAction instanceof String) {
                _battleScene.setTextToDisplay("Go back");
                if (keys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.DEFAULT;
                    this.selectedAction = 0;
                }
            } else if (selectedAction instanceof IMonsterMove) {
                IMonsterMove move = ((IMonsterMove) selectedAction);
                _battleScene.setTextToDisplay("Move: [" + move.getType() + "] " + move.getName() + ". Deals damage: " + move.getDamage());
                if (keys.isPressed(GameKeys.ENTER)) {
                    _battleSimulation.doMove(_battleSimulation.getState().getPlayer(), move);
                    this.menuState = MenuState.DEFAULT;
                    this.selectedAction = 0;
                }
            }
        }


        //Handle up/down for choosing actions
        Object[] currentlyShownActions = this._battleScene.getActions();
        if (selectedAction > currentlyShownActions.length - 1) {
            selectedAction = 0;
        }

        if (currentlyShownActions.length > 0) {
            if (keys.isPressed(GameKeys.DOWN)) {
                if (selectedAction < currentlyShownActions.length - 1) {
                    selectedAction++;
                } else {
                    selectedAction = 0;
                }
            }
            if (keys.isPressed(GameKeys.UP)) {
                if (selectedAction > 0) {
                    selectedAction--;
                } else {
                    selectedAction = currentlyShownActions.length - 1;
                }
            }
        }
    }

    @Override
    public void dispose() {
        _battleMusic.stop();
    }

    private void addEmptyAnimation(int duration, boolean autoStart) {
        EmptyAnimation emptyAnimation = new EmptyAnimation(duration);
        if (autoStart)
            emptyAnimation.start();
        blockingAnimations.add(emptyAnimation);
    }
}
