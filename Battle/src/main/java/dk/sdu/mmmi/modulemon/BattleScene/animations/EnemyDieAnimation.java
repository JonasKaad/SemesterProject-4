package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.common.drawing.Position;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class EnemyDieAnimation extends BaseAnimation {

    private BattleScene _battleScene;

    public EnemyDieAnimation(BattleScene battleScene) {
        super();
        Timeline = new int[]{0, 2000};
        States = new ArrayList<>(Timeline.length);

        //Initial state
        States.add(new float[]{
                BattleSceneDefaults.enemyMonsterPosition().getX(),BattleSceneDefaults.enemyMonsterPosition().getY(), // Enemy Monster,
                BattleSceneDefaults.enemyMonsterRotation() //rotation
        });

        States.add(new float[]{
                850, -1000, // Enemy Monster,
                3000f //rotation
        });

        this._battleScene = battleScene;
    }

    @Override
    public void update(GameData gameData) {
        super.tick();

        float[] states = super.getCurrentStates();
        this._battleScene.setEnemyMonsterPosition(new Position(states[0], states[1]));
        this._battleScene.setEnemyMonsterRotation(states[2]);
    }
}
