package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleView;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.battleevents.MonsterAttackMoveBattleEvent;
import dk.sdu.mmmi.modulemon.battleevents.TextDisplayBattleEvent;
import dk.sdu.mmmi.modulemon.battleevents.VictoryBattleEvent;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleSceneOpenAnimation;
import dk.sdu.mmmi.modulemon.battleview.animations.BattleViewAnimation;
import dk.sdu.mmmi.modulemon.battleview.animations.EmptyAnimation;
import dk.sdu.mmmi.modulemon.battleview.animations.PlayerBattleAttackAnimation;
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
        _battleMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/battle_music.ogg"));
        _attackSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/slam.ogg"));
        _winSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/you_won.ogg"));
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
            if (battleEvent instanceof MonsterAttackMoveBattleEvent) {
                MonsterAttackMoveBattleEvent event = (MonsterAttackMoveBattleEvent) battleEvent;
                if (event.getMonsterOwner().isPlayerControlled()) {
                    //Player attacked
                    PlayerBattleAttackAnimation battleAnimation = new PlayerBattleAttackAnimation(_battleScene, _attackSound);
                    battleAnimation.setOnEventDone(() -> {
                        addEmptyAnimation(1000);
                        _battleScene.setTextToDisplay("...");
                    });
                    battleAnimation.start();
                    blockingAnimations.add(battleAnimation);
                    _battleScene.setHealthIndicatorText(String.format("-%d HP", event.getDamageDealt()));
                } else {
                    //Enemy attacked
                    EmptyAnimation emptyAnimation = new EmptyAnimation(2000);
                    emptyAnimation.start();
                    blockingAnimations.add(emptyAnimation);
                }

                this._battleScene.setTextToDisplay(event.getText());
            } else if (battleEvent instanceof TextDisplayBattleEvent) {
                addEmptyAnimation(2000);
                this._battleScene.setTextToDisplay(battleEvent.getText());
            } else if (battleEvent instanceof VictoryBattleEvent) {
                EmptyAnimation emptyAnimation = new EmptyAnimation(2000);
                emptyAnimation.setOnEventDone(() -> gsm.setState(GameStateManager.MENU));
                emptyAnimation.start();
                blockingAnimations.add(emptyAnimation);
                this._winSound.play();
                this._battleScene.setTextToDisplay(battleEvent.getText());
            }
        }
    }

    @Override
    public void draw() {
        //Update information
        IMonster playerActiveMonster = _battleSimulation.getPlayer().getActiveMonster();
        _battleScene.setPlayerMonsterName(playerActiveMonster.getName());
        _battleScene.setPlayerHP(Integer.toString(playerActiveMonster.getHitPoints()));

        IMonster enemyActiveMonster = _battleSimulation.getEnemy().getActiveMonster();
        _battleScene.setEnemyMonsterName(enemyActiveMonster.getName());
        _battleScene.setEnemyHP(Integer.toString(enemyActiveMonster.getHitPoints()));

        _battleScene.setSelectedActionIndex(selectedAction);
        _battleScene.draw();
    }

    @Override
    public void handleInput() {
        if (!blockingAnimations.isEmpty()) {
            //If any blocking animations, don't allow any input.
            _battleScene.setActionBoxAlpha(0.5f);
            return;
        }
        _battleScene.setActionBoxAlpha(1f);

        if (menuState == MenuState.DEFAULT) {
            _battleScene.setActionTitle("Your actions:");
            _battleScene.setActions(this.defaultActions);

            String selectedAction = defaultActions[this.selectedAction % defaultActions.length];
            if (selectedAction.equalsIgnoreCase("Fight")) {
                _battleScene.setTextToDisplay("Choose a move to damage your opponent");
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.FIGHT;
                }
            } else if (selectedAction.equalsIgnoreCase("Switch")) {
                _battleScene.setTextToDisplay("[Not implemented] Change your active monster");
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    System.out.println("Switching monster isn't implemented yet");
                }
            } else if (selectedAction.equalsIgnoreCase("Animate")) {
                _battleScene.setTextToDisplay("Show a fancy pancy battle-animation");
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    System.out.println("Switching monster isn't implemented yet");
                    BattleViewAnimation openingAnimation = new BattleSceneOpenAnimation(_battleScene);
                    openingAnimation.start();
                    blockingAnimations.add(openingAnimation);
                }
            } else if (selectedAction.equalsIgnoreCase("Quit")) {
                _battleScene.setTextToDisplay("Quits the game");
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    System.out.println("Byee!!");
                    Gdx.app.exit();
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
                if (GameKeys.isPressed(GameKeys.ENTER)) {
                    this.menuState = MenuState.DEFAULT;
                }
            } else if (selectedAction instanceof IMonsterMove) {
                IMonsterMove move = ((IMonsterMove) selectedAction);
                _battleScene.setTextToDisplay("Move: [" + move.getType() + "] " + move.getName() + ". Deals damage: " + move.getDamage());
                if (GameKeys.isPressed(GameKeys.ENTER)) {
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
            if (GameKeys.isPressed(GameKeys.DOWN)) {
                if (selectedAction < currentlyShownActions.length - 1) {
                    selectedAction++;
                } else {
                    selectedAction = 0;
                }
            }
            if (GameKeys.isPressed(GameKeys.UP)) {
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

    private void addEmptyAnimation(int duration) {
        EmptyAnimation emptyAnimation = new EmptyAnimation(duration);
        emptyAnimation.start();
        blockingAnimations.add(emptyAnimation);
    }
}
