package dk.sdu.mmmi.modulemon.battleview.animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;

import java.util.ArrayList;

public class EnemyDieAnimation extends BattleViewAnimation{

    private BattleScene _battleScene;

    public EnemyDieAnimation(BattleScene battleScene) {
        super();
        Timeline = new int[]{0, 2000};
        States = new ArrayList<>(Timeline.length);

        //Initial state
        States.add(new float[]{
                850, 400, // Enemy Monster,
                0f //rotation
        });

        States.add(new float[]{
                850, -1000, // Enemy Monster,
                3000f //rotation
        });

        this._battleScene = battleScene;
    }

    @Override
    public void update(float dt) {
        super.tick();

        float[] states = super.getCurrentStates();
        this._battleScene.setEnemyMonsterPosition(new Position(states[0], states[1]));
        this._battleScene.setEnemyMonsterRotation(states[2]);
    }
}
