package dk.sdu.mmmi.modulemon.common.drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;

public class ImageDrawingUtils {

    private static final Object _instanceLock = new Object();
    private static ImageDrawingUtils instance;

    private Texture texture;

    private ImageDrawingUtils () {

    }

    public void drawImage(SpriteBatch batch, String spritePath, Class reference, float x, float y){
        texture = new Texture(new OSGiFileHandle(spritePath, reference));
        batch.draw(texture, x, y, 70, 70);
    }

    // Overloaded method to declare the wanted size of an image
    public void drawImage(SpriteBatch batch, String spritePath, Class reference, float x, float y, int width, int height){
        texture = new Texture(new OSGiFileHandle(spritePath, reference));
        batch.draw(texture, x, y, width, height);
    }

    public static ImageDrawingUtils getInstance(){
        if(instance == null){
            synchronized (_instanceLock){
                if(instance == null){
                    instance = new ImageDrawingUtils();
                }
            }
        }
        return instance;
    }
}
