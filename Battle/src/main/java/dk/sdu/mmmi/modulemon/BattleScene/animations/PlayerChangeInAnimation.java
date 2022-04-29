package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.AnimationCurves;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.drawing.Position;

import java.util.ArrayList;

public class PlayerChangeInAnimation extends BaseAnimation {
    private BattleScene battleScene;

    public PlayerChangeInAnimation(BattleScene battleScene) {
        super();
        this.battleScene = battleScene;
        super.animationCurve = AnimationCurves.EaseOutBounce();

        Timeline = new int[]{0, 1000};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX()-300, -100, //Player x,y
                3000f // Rotation
        });

        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX(), BattleSceneDefaults.playerMonsterPosition().getY(), //Enemy x,y
                0f //Rotation
        });

        battleScene.setPlayerMonsterPosition(new Position(States.get(0)[0], States.get(0)[1]));
    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = super.getCurrentStates();
        battleScene.setPlayerMonsterPosition(new Position(states[0], states[1]));
        battleScene.setPlayerMonsterRotation(states[2]);
    }
}
