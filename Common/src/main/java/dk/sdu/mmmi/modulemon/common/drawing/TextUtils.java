package dk.sdu.mmmi.modulemon.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;

public class TextUtils {
    private static final Object _instanceLock = new Object();
    private static TextUtils _instance;
    private GlyphLayout glyphLayout;
    private BitmapFont bigRobotoFont;
    private BitmapFont normalRobotoFont;
    private BitmapFont smallRobotoFont;
    private BitmapFont bigBoldRobotoFont;
    private BitmapFont normalBoldRobotoFont;
    private BitmapFont smallBoldRobotoFont;
    private TextUtils() {
        glyphLayout = new GlyphLayout();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(new OSGiFileHandle("/fonts/Roboto-Medium.ttf", this.getClass()));
        // Since we are using Roboto Medium, which looks a lot like Roboto-Bold,
        // the font size for the "bold" version is set to 2 higher, to give the feeling of it appearing bigger/bold.
        FreeTypeFontGenerator fontGeneratorBold = new FreeTypeFontGenerator(new OSGiFileHandle("/fonts/Roboto-Bold.ttf", this.getClass()));

        // Font size
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        bigRobotoFont = fontGenerator.generateFont(parameter);
        parameter.size = 36;
        bigBoldRobotoFont = fontGeneratorBold.generateFont(parameter);

        parameter.size = 24;
        normalRobotoFont = fontGenerator.generateFont(parameter);
        parameter.size = 26;
        normalBoldRobotoFont = fontGeneratorBold.generateFont(parameter);

        parameter.size = 16;
        smallRobotoFont = fontGenerator.generateFont(parameter);
        parameter.size = 18;
        smallBoldRobotoFont = fontGeneratorBold.generateFont(parameter);
        fontGenerator.dispose();
    }

    public static TextUtils getInstance(){
        if(_instance == null){
            synchronized (_instanceLock){
                if(_instance == null){
                    _instance = new TextUtils();
                }
            }
        }
        return _instance;
    }


    public void drawBigRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(bigRobotoFont, text);
        bigRobotoFont.setColor(color);
        bigRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }
    public void drawBigBoldRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(bigBoldRobotoFont, text);
        bigBoldRobotoFont.setColor(color);
        bigBoldRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }

    public void drawNormalRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(normalRobotoFont, text);
        normalRobotoFont.setColor(color);
        normalRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }

    public void drawNormalBoldRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(normalBoldRobotoFont, text);
        normalBoldRobotoFont.setColor(color);
        normalBoldRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }

    public void drawSmallRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(smallRobotoFont, text);
        smallRobotoFont.setColor(color);
        smallRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }

    public void drawSmallBoldRoboto(SpriteBatch batch, String text, Color color, float x, float y){
        glyphLayout.setText(smallBoldRobotoFont, text);
        smallBoldRobotoFont.setColor(color);
        smallBoldRobotoFont.draw(
                batch,
                text,
                x,
                y
        );
    }
}
