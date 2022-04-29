package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.AnimationCurves;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.drawing.Position;

import java.util.ArrayList;

public class EnemyChangeInAnimation extends BaseAnimation {
    private BattleScene battleScene;
    public EnemyChangeInAnimation(BattleScene battleScene){
        super();
        this.battleScene = battleScene;
        super.animationCurve = AnimationCurves.EaseOutBounce();

        Timeline = new int[]{0, 1000};
        States = new ArrayList<>(Timeline.length);

        States.add(new float[]{
                BattleSceneDefaults.enemyMonsterPosition().getX()+300, battleScene.getGameHeight() + 100, //Enemy x,y
                3000f // Rotation
        });

        States.add(new float[]{
                BattleSceneDefaults.enemyMonsterPosition().getX(), BattleSceneDefaults.enemyMonsterPosition().getY(), //Enemy x,y
                0f //Rotation
        });

        battleScene.setEnemyMonsterPosition(new Position(States.get(0)[0], States.get(0)[1]));
    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = super.getCurrentStates();
        battleScene.setEnemyMonsterPosition(new Position(states[0], states[1]));
        battleScene.setEnemyMonsterRotation(states[2]);
    }
}
