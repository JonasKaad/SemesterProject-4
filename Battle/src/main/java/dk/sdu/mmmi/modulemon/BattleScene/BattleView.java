package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.Battle.BattleSimulation;
import dk.sdu.mmmi.modulemon.BattleScene.animations.*;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleSceneMock.BattleParticipantMocks;
import dk.sdu.mmmi.modulemon.CommonBattle.BattleEvents.*;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.LinkedList;
import java.util.Queue;

public class BattleView implements IGameViewService, IBattleView {
    private IBattleSimulation _battleSimulation;
    private BattleScene _battleScene;
    private Music _battleMusic;
    private Sound _attackSound;
    private Sound _winSound;
    private MenuState menuState = MenuState.DEFAULT;
    private Queue<BattleViewAnimation> blockingAnimations;
    private Queue<BattleViewAnimation> backgroundAnimations;

    private String[] defaultActions;
    private int selectedAction = 0;


    /**
     * Creates the necessary variables used for custom fonts.
     */
    private SpriteBatch spriteBatch;

    public BattleView() {
        System.out.println("BATTLE VIEW BEING CONSTRUCTED!!!");
    }

    /**
     * Initialize for IBattleView
     */
    @Override
    public void init(IBattleParticipant player, IBattleParticipant enemy) {
        _battleSimulation = new BattleSimulation();
        _battleSimulation.StartBattle(player, enemy);
        blockingAnimations = new LinkedList<>();
        backgroundAnimations = new LinkedList<>();
        _battleMusic.play();
        _battleMusic.setVolume(0.1f);
        _battleMusic.setLooping(true);
        menuState = MenuState.DEFAULT;
        defaultActions = new String[]{"Fight", "Switch", "Animate", "Quit"};
        _battleScene.setActionTitle("Your actions:");
        _battleScene.setActions(this.defaultActions);

        BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
        openingAnimation.start();
        blockingAnimations.add(openingAnimation);
    }

    /**
     * Initialize for GameState
     */
    @Override
    public void init() {
        _battleMusic = Gdx.audio.newMusic(new OSGiFileHandle("/music/battle_music.ogg"));
        _attackSound = Gdx.audio.newSound(new OSGiFileHandle("/sounds/slam.ogg"));
        _winSound = Gdx.audio.newSound(new OSGiFileHandle("/sounds/you_won.ogg"));
        spriteBatch = new SpriteBatch();
        _battleScene = new BattleScene();
        init(BattleParticipantMocks.getPlayer(), BattleParticipantMocks.getOpponent());
    }

    @Override
    public void update(GameData gameData, IGameStateManager gameStateManager) {
        //spriteBatch.setProjectionMatrix(Game.cam.combined);
        if (_battleSimulation == null) {
            spriteBatch.begin();
            TextUtils.getInstance().drawBigRoboto(spriteBatch, "Waiting for battle participants", Color.WHITE, 100, gameData.getDisplayHeight() / 2f);
            spriteBatch.end();
            return;
        }

        //Is there any animations active?
        if (!blockingAnimations.isEmpty()) {
            BattleViewAnimation currentAnimation = blockingAnimations.peek();
            currentAnimation.update(gameData);
            if (!currentAnimation.isRunning()) {
                //If the animation is done, then we remove the animation from the queue
                currentAnimation.runEventDoneIfSet();
                blockingAnimations.remove();
                BattleViewAnimation nextAnimation = blockingAnimations.peek();
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
                enemyDieAnimation.setOnEventDone(() -> gameStateManager.setDefaultState());
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
        IMonster playerActiveMonster = _battleSimulation.getPlayer().getActiveMonster();
        _battleScene.setPlayerSprite(playerActiveMonster.getBackSprite());
        _battleScene.setPlayerMonsterName(playerActiveMonster.getName());
        _battleScene.setPlayerHP(Integer.toString(playerActiveMonster.getHitPoints()));

        IMonster enemyActiveMonster = _battleSimulation.getEnemy().getActiveMonster();
        _battleScene.setEnemySprite(enemyActiveMonster.getFrontSprite());
        _battleScene.setEnemyMonsterName(enemyActiveMonster.getName());
        _battleScene.setEnemyHP(Integer.toString(enemyActiveMonster.getHitPoints()));

        _battleScene.setSelectedActionIndex(selectedAction);
        _battleScene.draw();
    }

    @Override
    public void handleInput(GameData gameData, IGameStateManager gameStateManager) {
        if (!blockingAnimations.isEmpty()) {
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
                    BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
                    openingAnimation.start();
                    blockingAnimations.add(openingAnimation);
                }
            } else if (selectedAction.equalsIgnoreCase("Quit")) {
                _battleScene.setTextToDisplay("Ends the battle");
                if (keys.isPressed(GameKeys.ENTER)) {
                    gameStateManager.setDefaultState();
                }
            }
        } else if (menuState == MenuState.FIGHT) {
            _battleScene.setActionTitle("Moves:");
            IMonster playerMonster = _battleSimulation.getPlayer().getActiveMonster();

            Object[] monsterMoves = new Object[playerMonster.getMoves().size() + 1];
            monsterMoves[0] = "Cancel";
            for (int i = 1; i <= playerMonster.getMoves().size(); i++) {
                monsterMoves[i] = playerMonster.getMoves().get(i - 1);
            }
            _battleScene.setActions(monsterMoves);


            //Get selected move
            Object selectedAction = monsterMoves[this.selectedAction % monsterMoves.length];
            if (selectedAction instanceof String) {
                _battleScene.setTextToDisplay("Go back");
                if (keys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.DEFAULT;
                }
            } else if (selectedAction instanceof IMonsterMove) {
                IMonsterMove move = ((IMonsterMove) selectedAction);
                _battleScene.setTextToDisplay("Move: [" + move.getType() + "] " + move.getName() + ". Deals damage: " + move.getDamage());
                if (keys.isPressed(GameKeys.ENTER)) {
                    _battleSimulation.doMove(_battleSimulation.getPlayer(), move);
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
        if(autoStart)
            emptyAnimation.start();
        blockingAnimations.add(emptyAnimation);
    }
}