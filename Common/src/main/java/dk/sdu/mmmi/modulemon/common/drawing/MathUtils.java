package dk.sdu.mmmi.modulemon.common.drawing;

public class MathUtils {
    public static float map(float value, float inputMin, float inputMax, float outputMin, float outputMax){
        return (value - inputMin) * (outputMax - outputMin) / (inputMax - inputMin) + outputMin;
    }
}
