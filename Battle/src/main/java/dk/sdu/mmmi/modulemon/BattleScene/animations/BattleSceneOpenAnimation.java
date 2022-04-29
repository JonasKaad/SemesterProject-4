package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.common.animations.AnimationCurves;
import dk.sdu.mmmi.modulemon.common.drawing.Position;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class BattleSceneOpenAnimation extends BaseAnimation {

    private BattleScene _battleScene;

    public BattleSceneOpenAnimation(BattleScene battleScene) {
        super();
        super.animationCurve = AnimationCurves.EaseOut();
        this._battleScene = battleScene;
    }

    public void setInitialState(){
        Timeline = new int[]{0, 1200, 2000};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                _battleScene.getGameWidth(), 0, //Backdrop
                _battleScene.getGameWidth() * 1.25f, 400, //Enemybase
                0 - _battleScene.getGameWidth() * 1.25f, 0, // Playerbase
                300, -500, //Player mon
                850, _battleScene.getGameWidth() + 500f, //Enemy mon,
                -500f, 550, //enemy health box,
                -500f, 300, //player health box,
                _battleScene.getGameWidth() + 500, 155,  // Action box position
                BattleSceneDefaults.textBoxPosition().getX(), -BattleSceneDefaults.textBoxHeight() - 20 // Text box position
        });

        States.add(new float[]{
                0, 0,
                800, 400,
                145, 0,
                300, -500,
                850, _battleScene.getGameHeight() + 500f,
                -500f, 550,
                -500f, 300,
                _battleScene.getGameWidth() + 500, 155,
                BattleSceneDefaults.textBoxPosition().getX(), -BattleSceneDefaults.textBoxHeight() - 20
        });

        States.add(new float[]{
                BattleSceneDefaults.backdropPosition().getX(), BattleSceneDefaults.backdropPosition().getY(),
                BattleSceneDefaults.enemyBasePosition().getX(), BattleSceneDefaults.enemyBasePosition().getY(),
                BattleSceneDefaults.playerBasePosition().getX(), BattleSceneDefaults.playerBasePosition().getY(),
                BattleSceneDefaults.playerMonsterPosition().getX(), BattleSceneDefaults.playerMonsterPosition().getY(),
                BattleSceneDefaults.enemyMonsterPosition().getX(), BattleSceneDefaults.enemyMonsterPosition().getY(),
                BattleSceneDefaults.enemyHealthBoxPosition().getX(), BattleSceneDefaults.enemyHealthBoxPosition().getY(),
                BattleSceneDefaults.playerHealthBoxPosition().getX(), BattleSceneDefaults.playerHealthBoxPosition().getY(),
                BattleSceneDefaults.actionBoxPosition(_battleScene.getGameWidth()).getX(), BattleSceneDefaults.actionBoxPosition(_battleScene.getGameWidth()).getY(),
                BattleSceneDefaults.textBoxPosition().getX(), BattleSceneDefaults.textBoxPosition().getY()
        });

        _battleScene.setBackdropPosition(new Position(_battleScene.getGameWidth(), 0));
        _battleScene.setEnemyBasePosition(new Position(_battleScene.getGameWidth() * 1.25f, 400));
        _battleScene.setPlayerBasePosition(new Position(0 - _battleScene.getGameWidth() * 1.25f, 0));
        _battleScene.setPlayerMonsterPosition(new Position(300, -500));
        _battleScene.setEnemyMonsterPosition(new Position(850, _battleScene.getGameHeight() + 500));
        _battleScene.setEnemyHealthBoxPosition(new Position(-500f, 550));
        _battleScene.setPlayerHealthBoxPosition(new Position(-500f, 300));
        _battleScene.setActionBoxPosition(new Position(_battleScene.getGameWidth() + 500, 135));
        _battleScene.setTextBoxPosition(new Position(BattleSceneDefaults.textBoxPosition().getX(), -BattleSceneDefaults.textBoxHeight() - 20));
    }

    @Override
    public void start() {
        setInitialState();
        super.start();
    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = getCurrentStates();
        _battleScene.setBackdropPosition(new Position(states[0], states[1]));
        _battleScene.setEnemyBasePosition(new Position(states[2], states[3]));
        _battleScene.setPlayerBasePosition(new Position(states[4], states[5]));
        _battleScene.setPlayerMonsterPosition(new Position(states[6], states[7]));
        _battleScene.setEnemyMonsterPosition(new Position(states[8], states[9]));
        _battleScene.setEnemyHealthBoxPosition(new Position(states[10], states[11]));
        _battleScene.setPlayerHealthBoxPosition(new Position(states[12], states[13]));
        _battleScene.setActionBoxPosition(new Position(states[14], states[15]));
        _battleScene.setTextBoxPosition(new Position(states[16], states[17]));
    }
}
