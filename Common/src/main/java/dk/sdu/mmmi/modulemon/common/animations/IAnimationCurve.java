package dk.sdu.mmmi.modulemon.common.animations;

public interface IAnimationCurve {
    float getValue(float start, float goal, float time);
}
