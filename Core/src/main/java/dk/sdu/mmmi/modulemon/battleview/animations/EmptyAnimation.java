package dk.sdu.mmmi.modulemon.battleview.animations;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;

import java.util.ArrayList;

public class EmptyAnimation extends BattleViewAnimation{

    public EmptyAnimation(int duration) {
        super();
        Timeline = new int[]{0, duration};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[0]);
        States.add(new float[0]);
    }


    @Override
    public void update(float dt) {
        super.tick();
        getCurrentStates();
    }
}
