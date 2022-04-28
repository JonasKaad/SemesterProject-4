package dk.sdu.mmmi.modulemon.BattleScene.animations;

import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleSceneDefaults;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.drawing.Position;

import java.util.ArrayList;

public class PlayerDieAnimation extends BaseAnimation {

    private BattleScene battleScene;
    public PlayerDieAnimation(BattleScene battleScene){
        super();
        Timeline = new int[]{0, 2000};
        States = new ArrayList<>(Timeline.length);

        //Initial state
        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX(),BattleSceneDefaults.playerMonsterPosition().getY(), // Player Monster,
                BattleSceneDefaults.playerMonsterRotation() //rotation
        });

        States.add(new float[]{
                BattleSceneDefaults.playerMonsterPosition().getX(), -1000, // Enemy Monster,
                3000f //rotation
        });

        this.battleScene = battleScene;
    }

    @Override
    public void update(GameData gameData) {
        super.tick();

        float[] states = super.getCurrentStates();
        this.battleScene.setPlayerMonsterPosition(new Position(states[0], states[1]));
        this.battleScene.setPlayerMonsterRotation(states[2]);
    }
}
