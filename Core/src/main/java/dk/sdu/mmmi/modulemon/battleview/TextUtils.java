package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import dk.sdu.mmmi.modulemon.main.Game;

public class TextUtils {
    private static final Object _instanceLock = new Object();
    private static TextUtils _instance;
    private GlyphLayout glyphLayout;
    private BitmapFont bigRobotoFont;
    private BitmapFont normalRobotoFont;
    private BitmapFont smallRobotoFont;
    private TextUtils() {
        glyphLayout = new GlyphLayout();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/Roboto-Medium.ttf"));

        // Font size
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;

        bigRobotoFont = fontGenerator.generateFont(parameter);

        parameter.size = 24;
        normalRobotoFont = fontGenerator.generateFont(parameter);

        parameter.size = 16;
        smallRobotoFont = fontGenerator.generateFont(parameter);
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
}
