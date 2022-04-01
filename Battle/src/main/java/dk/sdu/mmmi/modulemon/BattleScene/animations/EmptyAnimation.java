package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class EmptyAnimation extends BaseAnimation {

    public EmptyAnimation(int duration) {
        super();
        Timeline = new int[]{0, duration};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[0]);
        States.add(new float[0]);
    }


    @Override
    public void update(GameData gameData) {
        super.tick();
        getCurrentStates();
    }
}
