package dk.sdu.mmmi.modulemon.battleview.animations;

public interface IAnimationCurve {
    float getValue(float start, float goal, float time);
}
