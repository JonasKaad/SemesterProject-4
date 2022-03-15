package dk.sdu.mmmi.modulemon.battleview.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.battleview.Position;

public class BattleScene {

    private Image _backdrop;
    private Position _backdropPosition;
    private SpriteBatch spriteBatch;

    public BattleScene(){
        _backdrop = new Image(new Texture(Gdx.files.internal("assets/backdrops/backdrop1.png")));
        spriteBatch = new SpriteBatch();
        resetPositions();
    }

    public void draw(){
        spriteBatch.begin();
        _backdrop.draw(spriteBatch, 1);
        _backdrop.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        _backdropPosition.updatePosition(_backdrop);
        spriteBatch.end();
    }


    /* POSITION SHENANIGANS */

    public Position getBackdropPosition() {
        return _backdropPosition;
    }

    public void setBackdropPosition(Position _backdropPosition) {
        this._backdropPosition = _backdropPosition;
    }

    private void resetPositions(){
        _backdropPosition = new Position(0,0);
    }
}
