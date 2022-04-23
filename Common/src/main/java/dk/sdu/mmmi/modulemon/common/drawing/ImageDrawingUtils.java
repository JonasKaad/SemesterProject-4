package dk.sdu.mmmi.modulemon.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;

public class ImageDrawingUtils {

    private static final Object _instanceLock = new Object();
    private static ImageDrawingUtils instance;

    private GlyphLayout glyphLayout;

    private Texture texture;
    private Image image;

    private ImageDrawingUtils () {

    }

    public void drawImage(SpriteBatch batch, String spritePath, Class reference, float x, float y){
        this.image = new Image(new Texture(new OSGiFileHandle(spritePath, reference)));
        //batch.draw(image, x, y);
        image.setOrigin(x, y);
        image.draw(batch, 1);
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
