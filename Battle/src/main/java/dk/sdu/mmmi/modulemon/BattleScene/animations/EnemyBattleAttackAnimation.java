package dk.sdu.mmmi.modulemon.BattleScene.animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.modulemon.BattleScene.Position;
import dk.sdu.mmmi.modulemon.BattleScene.scenes.BattleScene;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class EnemyBattleAttackAnimation extends BaseAnimation {

    private BattleScene _battleScene;
    private Sound _attackSound;
    private boolean _attackSoundPlayed = false;

    public EnemyBattleAttackAnimation(BattleScene battleScene, Sound attackSound) {
        super();
        Timeline = new int[]{0, 300, 450, 730};
        States = new ArrayList<>(Timeline.length);

        //Initial state
        States.add(new float[]{
                300, 120, // Player monster
                850, 400, // Enemy Monster,
                330, 100, 0, // Attack indicator + opacity,
                0 // Play sound queue
        });

        //Først flyv enemy monster ned til player for at attack
        States.add(new float[]{
                300, 120, // Player monster
                300, 80, // Enemy Monster,
                330, 100, 0, // Attack indicator + opacity
                0 // Play sound queue
        });

        // Player falder tilbage
        States.add(new float[]{
                250, 50, // Player monster
                850, 400, // Enemy Monster,
                330, 150, 1, // Attack indicator + opacity,
                1 // Play sound queue
        });

        //Det hele resettes,
        States.add(new float[]{
                300, 120, // Player monster
                850, 400, // Enemy Monster,
                330, 200, 0, // Attack indicator + opacity,
                0 // Play sound queue
        });

        battleScene.setHealthIndicatorColor(new Color(1,0,0, 0));
        battleScene.setHealthIndicatorPosition(new Position(330, 100));

        this._battleScene = battleScene;
        this._attackSound = attackSound;
    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = super.getCurrentStates();
        _battleScene.setPlayerMonsterPosition(new Position(states[0], states[1]));
        _battleScene.setEnemyMonsterPosition(new Position(states[2], states[3]));
        _battleScene.setHealthIndicatorPosition(new Position(states[4], states[5]));
        _battleScene.setHealthIndicatorColor(new Color(1,0,0, states[6]));
        if(states[7] > 0.9f){
            if(!_attackSoundPlayed) {
                _attackSound.play();
                _attackSoundPlayed = true;
            }
        }
    }
}
