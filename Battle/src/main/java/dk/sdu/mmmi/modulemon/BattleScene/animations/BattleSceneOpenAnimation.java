package dk.sdu.mmmi.modulemon.BattleScene.animations;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.modulemon.BattleScene.Position;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class BattleSceneOpenAnimation extends BattleViewAnimation {

    private BattleScene _battleScene;

    public BattleSceneOpenAnimation(BattleScene battleScene) {
        super();
        this._battleScene = battleScene;
    }

    public void setInitialState(){
        Timeline = new int[]{0, 2000, 2500};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                _battleScene.getGameWidth(), 0, //Backdrop
                _battleScene.getGameWidth() * 1.25f, 400, //Enemybase
                0 - _battleScene.getGameWidth() * 1.25f, 0, // Playerbase
                300, -500, //Player mon
                850, _battleScene.getGameWidth() + 500f, //Enemy mon,
                -500f, 550, //enemy health box,
                -500f, 300, //player health box,
                _battleScene.getGameWidth() + 500, 135  // Action box position
        });

        States.add(new float[]{
                0, 0,
                800, 400,
                145, 0,
                300, -500,
                850, _battleScene.getGameHeight() + 500f,
                -500f, 550,
                -500f, 300,
                _battleScene.getGameWidth() + 500, 135
        });

        States.add(new float[]{
                0, 0,
                800, 400,
                145, -8,
                300, 120,
                850, 400,
                480, 550,
                100, 300,
                _battleScene.getGameWidth() - 300, 135
        });

        _battleScene.setBackdropPosition(new Position(_battleScene.getGameWidth(), 0));
        _battleScene.setEnemyBasePosition(new Position(_battleScene.getGameWidth() * 1.25f, 400));
        _battleScene.setPlayerBasePosition(new Position(0 - _battleScene.getGameWidth() * 1.25f, 0));
        _battleScene.setPlayerMonsterPosition(new Position(300, -500));
        _battleScene.setEnemyMonsterPosition(new Position(850, _battleScene.getGameHeight() + 500));
        _battleScene.setEnemyHealthBoxPosition(new Position(-500f, 550));
        _battleScene.setPlayerHealthBoxPosition(new Position(-500f, 300));
        _battleScene.setActionBoxPosition(new Position(_battleScene.getGameWidth() + 500, 135));
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
    }
}
