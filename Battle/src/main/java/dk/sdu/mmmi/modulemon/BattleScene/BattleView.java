package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.modulemon.BattleScene.animations.*;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleSceneMock.BattleParticipantMocks;
import dk.sdu.mmmi.modulemon.CommonBattle.*;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleCallback;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.*;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents.*;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.drawing.PersonaRectangle;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Queue;


public class BattleView implements IGameViewService, IBattleView {

    private boolean updateHasRunOnce;
    private boolean _isInitialized;
    private IBattleCallback _battleCallback;
    private IBattleSimulation _battleSimulation;
    private BattleScene _battleScene;
    private Music _battleMusic;
    private Sound _winSound;
    private MenuState menuState = MenuState.DEFAULT;
    private Queue<BaseAnimation> blockingAnimations;
    private Queue<BaseAnimation> backgroundAnimations;

    private String[] defaultActions;
    private int selectedAction = 0;

    private IGameStateManager gameStateManager;


    /**
     * Creates the necessary variables used for custom fonts.
     */
    private SpriteBatch spriteBatch;

    public Sound getAttackSound(IMonsterMove monsterMove){
        Sound returnSound = null;

        try {
            returnSound = Gdx.audio.newSound(new OSGiFileHandle(monsterMove.getSoundPath(), monsterMove.getClass()));
        }catch(GdxRuntimeException ex){
            System.out.println("[Warning] Failed to loadd attack sound for monster-move: " + monsterMove.getName());
        }

        return returnSound;
    }

    public BattleView() {
        System.out.println("BATTLE VIEW BEING CONSTRUCTED!!!");
        _isInitialized = false;
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();
        menuState = MenuState.DEFAULT;

        defaultActions = new String[]{"Fight", "Monsters", "Animate", "Style", "Quit"};
    }

    /**
     * Initialize for IBattleView
     */
    public void startBattle(IBattleParticipant player, IBattleParticipant enemy, IBattleCallback callback) {
        if(player == null && enemy == null){
            try {
                player = BattleParticipantMocks.getPlayer();
                enemy = BattleParticipantMocks.getOpponent();
            } catch (IOException | URISyntaxException e){
                System.out.println("Failed to get monster mocks");
                e.printStackTrace();
            }

        }
        selectedAction = 0;
        _battleMusic = Gdx.audio.newMusic(new OSGiFileHandle("/music/battle_music.ogg", this.getClass()));
        _winSound = Gdx.audio.newSound(new OSGiFileHandle("/sounds/you_won.ogg", this.getClass()));
        _battleSimulation.StartBattle(player, enemy);
        _battleCallback = callback;
        _battleMusic.play();
        _battleMusic.setVolume(0.1f);
        _battleMusic.setLooping(true);
        menuState = MenuState.DEFAULT;
        _battleScene.setActionTitle("Your actions:");
        _battleScene.setActions(this.defaultActions);
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();

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

    public void handleBattleEnd(VictoryBattleEvent victoryBattleEvent) {
        if (_battleCallback != null) {
            _battleCallback.onBattleEnd(new BattleResult(victoryBattleEvent.getWinner(), _battleSimulation.getState().getPlayer(), _battleSimulation.getState().getEnemy()));
        } else {
            gameStateManager.setDefaultState();
        }
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init(IGameStateManager gameStateManager) {
        spriteBatch = new SpriteBatch();
        _battleScene = new BattleScene();

        updateHasRunOnce = false;
        _isInitialized = true;
        this.gameStateManager = gameStateManager;
    }

    //OSGi dependency injection
    public void setBattleSimulation(IBattleSimulation battleSimulation) {
        System.out.println("BattleSimulation set in BattleView");
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
        if (!_isInitialized) {
            return;
        }
        if (_battleSimulation == null) {
            spriteBatch.begin();
            TextUtils.getInstance().drawBigRoboto(spriteBatch, "Waiting for battle participants", Color.WHITE, 100, gameData.getDisplayHeight() / 2f);
            spriteBatch.end();
            return;
        }

        updateHasRunOnce = true;

        //Is there any animations active?
        if (!blockingAnimations.isEmpty()) {
            //Set in update() because some animations depend on it.
            if (_battleScene != null) {
                _battleScene.setGameHeight(gameData.getDisplayHeight());
                _battleScene.setGameWidth(gameData.getDisplayWidth());
            }

            BaseAnimation currentAnimation = blockingAnimations.peek();
            if (!currentAnimation.isStarted()) {
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
                    PlayerBattleAttackAnimation battleAnimation = new PlayerBattleAttackAnimation(_battleScene, getAttackSound(event.getMove()));
                    battleAnimation.setOnEventDone(() -> {
                        addEmptyAnimation(1000, true);
                        _battleScene.setTextToDisplay("...");
                    });
                    battleAnimation.start();
                    blockingAnimations.add(battleAnimation);
                    _battleScene.setHealthIndicatorText(String.format("-%d HP", event.getDamage()));
                } else {
                    //Enemy attacked
                    EnemyBattleAttackAnimation battleAnimation = new EnemyBattleAttackAnimation(_battleScene, getAttackSound(event.getMove()));
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
        if(!updateHasRunOnce){
            // In order to not draw before the Opening Animation has moved things out of the way, we wait till update() has been called once.
            // This is because we induce a race-condition when changing game-states in the GameStateManager.
            // This usually dosn't mean a lot, but because of the way this class is written, it's important that update() is called before draw(), otherwise we would have a ghost frame in the beginning.
            return;
        }
        //Game data
        _battleScene.setGameHeight(gameData.getDisplayHeight());
        _battleScene.setGameWidth(gameData.getDisplayWidth());

        //Update information
        if (_battleSimulation != null) {
            IMonster playerActiveMonster = _battleSimulation.getState().getPlayer().getActiveMonster();
            _battleScene.setPlayerSprite(playerActiveMonster.getBackSprite(), playerActiveMonster.getClass());
            _battleScene.setPlayerMonsterName(playerActiveMonster.getName());
            _battleScene.setPlayerHP(playerActiveMonster.getHitPoints());
            _battleScene.setMaxPlayerHP(playerActiveMonster.getMaxHitPoints());

            IMonster enemyActiveMonster = _battleSimulation.getState().getEnemy().getActiveMonster();
            _battleScene.setEnemySprite(enemyActiveMonster.getFrontSprite(), enemyActiveMonster.getClass());
            _battleScene.setEnemyMonsterName(enemyActiveMonster.getName());
            _battleScene.setEnemyHP(enemyActiveMonster.getHitPoints());
            _battleScene.setMaxEnemyHP(enemyActiveMonster.getMaxHitPoints());
        }

        _battleScene.setSelectedActionIndex(selectedAction);
        _battleScene.draw(gameData.getDelta(), gameData.getCamera());
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
                    this.selectedAction = 0;
                    this.menuState = MenuState.FIGHT;
                }
            } else if (selectedAction.equalsIgnoreCase("Monsters")) {
                _battleScene.setTextToDisplay("Change your active monster");
                if (keys.isPressed(GameKeys.ENTER)) {
                    this.selectedAction = 0;
                    this.menuState = MenuState.SWITCH;
                }
            } else if (selectedAction.equalsIgnoreCase("Animate")) {
                _battleScene.setTextToDisplay("Show a fancy pancy battle-animation");
                if (keys.isPressed(GameKeys.ENTER)) {
                    BaseAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
                    openingAnimation.start();
                    blockingAnimations.add(openingAnimation);
                }
            } else if (selectedAction.equalsIgnoreCase("Style")) {
                _battleScene.setTextToDisplay("Change box-styles");
                if (keys.isPressed(GameKeys.ENTER)) {
                    if (_battleScene.getPlayerBoxRect() instanceof PersonaRectangle) {
                        _battleScene.setPlayerBoxRectStyle(Rectangle.class);
                        _battleScene.setEnemyBoxRectStyle(Rectangle.class);
                        _battleScene.setActionBoxRectStyle(Rectangle.class);
                        _battleScene.setTextBoxRectStyle(Rectangle.class);
                    } else {
                        _battleScene.setPlayerBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setEnemyBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setActionBoxRectStyle(PersonaRectangle.class);
                        _battleScene.setTextBoxRectStyle(PersonaRectangle.class);
                    }
                }
            } else if (selectedAction.equalsIgnoreCase("Quit")) {
                _battleScene.setTextToDisplay("Ends the battle");
                if (keys.isPressed(GameKeys.ENTER)) {
                    handleBattleEnd(new VictoryBattleEvent("Player runs away", _battleSimulation.getState().getEnemy()));
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
        } else if (this.menuState == MenuState.SWITCH) {
            _battleScene.setActionTitle("Your monsters:");
            IBattleParticipant player = _battleSimulation.getState().getPlayer();
            Object[] monsters = new Object[player.getMonsterTeam().size() + 1];
            monsters[monsters.length - 1] = "Cancel";

            for(int i = 0; i < player.getMonsterTeam().size(); i++){
                monsters[i] = player.getMonsterTeam().get(i);
            }
            _battleScene.setActions(monsters);

            //Get selected monster
            Object selectedAction = monsters[this.selectedAction % monsters.length];
            if (selectedAction instanceof String) {
                _battleScene.setTextToDisplay("Go back");
                if (keys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.DEFAULT;
                    this.selectedAction = 0;
                }
            } else if (selectedAction instanceof IMonster) {
                IMonster monster = ((IMonster) selectedAction);
                if(!monster.equals(player.getActiveMonster())) {
                    if(monster.getHitPoints() > 0) {
                        _battleScene.setTextToDisplay(String.format("Switch to '%s'? ", monster.getName()));

                        if (keys.isPressed(GameKeys.ENTER)) {
                            _battleSimulation.switchMonster(player, monster);
                            this.menuState = MenuState.DEFAULT;
                            this.selectedAction = 0;
                        }
                    }else{
                        _battleScene.setTextToDisplay("This monster is dead. It cannot battle");
                    }
                }else{
                    _battleScene.setTextToDisplay("This monster is already in battle.");
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
        _battleMusic = null; //Unload Battle Music
    }

    private void addEmptyAnimation(int duration, boolean autoStart) {
        EmptyAnimation emptyAnimation = new EmptyAnimation(duration);
        if (autoStart)
            emptyAnimation.start();
        blockingAnimations.add(emptyAnimation);
    }
}
