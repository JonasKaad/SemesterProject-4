package dk.sdu.mmmi.modulemon.battleview.animations;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleSceneOpenAnimation extends BattleViewAnimation{

    private BattleScene _battleScene;

    public BattleSceneOpenAnimation(BattleScene battleScene){
        super();
        Timeline = new int[]{0,1000};

        //States: Backdrop_x, Backdrop_y
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{Gdx.graphics.getWidth(),0});
        States.add(new float[]{0,0});

        battleScene.setBackdropPosition(new Position(Gdx.graphics.getWidth(), 0));
        this._battleScene = battleScene;
    }

    @Override
    public void update(float dt){
        super.tick(dt);
        float[] states = getCurrentStates();
        _battleScene.setBackdropPosition(new Position(states[0], states[1]));
    }
}
