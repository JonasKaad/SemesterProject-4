package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.AnimationCurves;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.drawing.Position;

import java.util.ArrayList;

public class PlayerChangeOutAnimation extends BaseAnimation {
    private BattleScene battleScene;

    public PlayerChangeOutAnimation(BattleScene battleScene) {
        super();
        this.battleScene = battleScene;
        super.animationCurve = AnimationCurves.EaseOut();

        Timeline = new int[]{0, 600};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX(), BattleSceneDefaults.playerMonsterPosition().getY(), //Enemy x,y
                0f //Rotation
        });

        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX()- battleScene.getGameWidth(), BattleSceneDefaults.playerMonsterPosition().getY(), //Enemy x,y
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
