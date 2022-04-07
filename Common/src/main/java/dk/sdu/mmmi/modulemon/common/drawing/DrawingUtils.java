package dk.sdu.mmmi.modulemon.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DrawingUtils {

    public static void borderedRect(ShapeRenderer shapeRenderer, float x, float y, float width, float height, Color borderColor, Color fillColor, float borderWidth ){
        //Border box
        shapeRenderer.setColor(borderColor);
        shapeRenderer.rect(
                x - borderWidth,
                 y - borderWidth,
                width + borderWidth*2,
                height + borderWidth*2
        );

        //Filler
        shapeRenderer.setColor(fillColor);
        shapeRenderer.rect(
                x,
                y,
                width,
                height
        );
    }


}
