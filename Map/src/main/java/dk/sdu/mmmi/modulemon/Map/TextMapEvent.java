package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.CommonMap.IMapEvent;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;

import java.util.Queue;

public class TextMapEvent implements IMapEvent {
    private Queue<String> lines;
    private Rectangle textBox;
    public TextMapEvent(Queue<String> lines){
        if(lines == null || lines.isEmpty()){
            throw new IllegalArgumentException("Argument 'lines' is null or has no elements");
        }
        this.lines = lines;
        textBox = new Rectangle(20,20, -1, -1);
    }

    public void addLine(String line){
        this.lines.add(line);
    }

    @Override
    public boolean isEventDone() {
        return lines.isEmpty();
    }

    @Override
    public void start(GameData gameData) {
        // Empty
    }

    @Override
    public void update(GameData gameData) {
        textBox.setHeight(100f);
        textBox.setWidth(gameData.getDisplayWidth() - 50);
    }

    @Override
    public void draw(GameData gameData, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        //Draw rectangle
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        textBox.draw(shapeRenderer, gameData.getDelta());
        shapeRenderer.end();

        //Animate triangle-thing

        //Draw text
        spriteBatch.begin();
        TextUtils.getInstance().drawNormalRoboto(spriteBatch,
                lines.peek(),
                Color.BLACK,
                textBox.getX() + 20,
                textBox.getY() + 80
        );
        spriteBatch.end();
    }

    @Override
    public void handleInput(GameData gameData) {
        if(gameData.getKeys().isPressed(GameKeys.ACTION)){
            this.lines.poll();
        }
    }
}
