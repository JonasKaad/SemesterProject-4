package dk.sdu.mmmi.modulemon.BattleScene.animations;

public class AnimationCurves {

    public static IAnimationCurve Linear() {
        return (start, end, t) -> start + t * (end - start);
    }

}
