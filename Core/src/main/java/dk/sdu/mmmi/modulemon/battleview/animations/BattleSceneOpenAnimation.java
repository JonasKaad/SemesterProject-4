package dk.sdu.mmmi.modulemon.battleview.animations;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;

import java.util.ArrayList;

public class BattleSceneOpenAnimation extends BattleViewAnimation {

    private BattleScene _battleScene;

    public BattleSceneOpenAnimation(BattleScene battleScene) {
        super();
        Timeline = new int[]{0, 2000, 2500};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                Gdx.graphics.getWidth(), 0, //Backdrop
                Gdx.graphics.getWidth() * 1.25f, 400, //Enemybase
                0 - Gdx.graphics.getWidth() * 1.25f, 0, // Playerbase
                300, -500, //Player mon
                850, Gdx.graphics.getHeight() + 500f, //Enemy mon,
                -500f, 550, //enemy health box,
                -500f, 300, //player health box,
               Gdx.graphics.getWidth() + 500, 125  // Action box position
        });

        States.add(new float[]{
                0, 0,
                800, 400,
                200, 0,
                300, -500,
                850, Gdx.graphics.getHeight() + 500f,
                -500f, 550,
                -500f, 300,
                Gdx.graphics.getWidth() + 500, 125
        });

        States.add(new float[]{
                0, 0,
                800, 400,
                200, 120,
                300, 120,
                850, 400,
                480, 550,
                100, 300,
                Gdx.graphics.getWidth() - 300, 125
        });

        battleScene.setBackdropPosition(new Position(Gdx.graphics.getWidth(), 0));
        battleScene.setEnemyBasePosition(new Position(Gdx.graphics.getWidth() * 1.25f, 400));
        battleScene.setPlayerBasePosition(new Position(0 - Gdx.graphics.getWidth() * 1.25f, 0));
        battleScene.setPlayerMonsterPosition(new Position(300, -500));
        battleScene.setEnemyMonsterPosition(new Position(850, Gdx.graphics.getHeight() + 500));
        battleScene.setEnemyHealthBoxPosition(new Position(Gdx.graphics.getWidth() - 500f, 300));
        battleScene.setActionBoxPosition(new Position(Gdx.graphics.getWidth() + 500, 125));
        this._battleScene = battleScene;
    }

    @Override
    public void update(float dt) {
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
