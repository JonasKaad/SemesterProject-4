package dk.sdu.mmmi.modulemon.common.animations;

import dk.sdu.mmmi.modulemon.common.drawing.MathUtils;

public class AnimationCurves {

    public static IAnimationCurve Linear() {
        return (start, end, t) -> start + t * (end - start);
    }

    public static IAnimationCurve CubicBezier(float x1, float y1, float x2, float y2){
        return (start, goal, t) -> {
            // Lav bezier time
            float bezierTime = CubicBezierEasing.interpolate(t, x1, y1, x2, y2);
            return MathUtils.map(bezierTime, 0, 1, start, goal);
        };
    }

    public static IAnimationCurve EaseOut(){
        return CubicBezier(0f,0f,.58f,1f);
    }

    public static IAnimationCurve EaseIn(){
        return CubicBezier(.42f,0f,1f,1f);
    }

    public static IAnimationCurve EaseOutBounce(){
        return CubicBezier(.31f, .57f, 0, 1.1f);
    }
    private static class CubicBezierEasing
    {
        public static float interpolate( float fraction, float x1, float y1, float x2, float y2 ) {
            if( fraction <= 0 || fraction >= 1 )
                return fraction;

            // use binary search
            float low = 0;
            float high = 1;
            while( true ) {
                float mid = (low + high) / 2;
                float estimate = cubicBezier( mid, x1, x2 );
                if( Math.abs( fraction - estimate ) < 0.0005f )
                    return cubicBezier( mid, y1, y2 );
                if( estimate < fraction )
                    low = mid;
                else
                    high = mid;
            }
        }

        private static float cubicBezier( float t, float xy1, float xy2 ) {
            float invT = (1 - t);
            float b1 = 3 * t * (invT * invT);
            float b2 = 3 * (t * t) * invT;
            float b3 = t * t * t;
            return (b1 * xy1) + (b2 * xy2) + b3;
        }
    }
}
