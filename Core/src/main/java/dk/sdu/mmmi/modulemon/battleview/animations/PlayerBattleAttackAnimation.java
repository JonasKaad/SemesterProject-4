package dk.sdu.mmmi.modulemon.battleview.animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.scenes.BattleScene;

import java.util.ArrayList;

public class PlayerBattleAttackAnimation extends BattleViewAnimation{

    private BattleScene _battleScene;
    private Sound _attackSound;
    private boolean _attackSoundPlayed = false;

    public PlayerBattleAttackAnimation(BattleScene battleScene, Sound attackSound) {
        super();
        Timeline = new int[]{0, 300, 450, 730};
        States = new ArrayList<>(Timeline.length);

        //Initial state
        States.add(new float[]{
                300, 80, // Player monster
                850, 400, // Enemy Monster,
                900, 500, 0, // Attack indicator + opacity,
                0 // Play sound queue
        });

        //Først flyv player monster op til enemy for at attack
        States.add(new float[]{
                850, 400, // Player monster
                850, 400, // Enemy Monster,
                900, 500, 0, // Attack indicator + opacity
                0 // Play sound queue
        });

        // Enemy falder tilbage
        States.add(new float[]{
                300, 80, // Player monster
                900, 430, // Enemy Monster,
                900, 550, 1, // Attack indicator + opacity,
                1 // Play sound queue
        });

        //Det hele resettes,
        States.add(new float[]{
                300, 80, // Player monster
                850, 400, // Enemy Monster,
                900, 600, 0, // Attack indicator + opacity,
                0 // Play sound queue
        });

        battleScene.setHealthIndicatorColor(new Color(1,0,0, 0));
        battleScene.setHealthIndicatorPosition(new Position(900,500));

        this._battleScene = battleScene;
        this._attackSound = attackSound;
    }

    @Override
    public void update(float dt) {
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
