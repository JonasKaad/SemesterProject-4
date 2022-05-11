package dk.sdu.mmmi.modulemon.Interaction;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityType;
import dk.sdu.mmmi.modulemon.CommonMap.IMapEvent;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;

import java.util.Queue;

public class BattleEvent implements IMapEvent {
    private Queue<String> lines;
    private Rectangle textBox;
    private Rectangle exlamationBox;
    private Entity aggresor;
    private Entity victim;
    private IMapView mapView;
    private boolean battleStarted;

    public BattleEvent(Queue<String> lines, Entity aggressor, Entity victim, IMapView map){
        if(lines == null || lines.isEmpty()){
            throw new IllegalArgumentException("Argument 'lines' is null or has no elements");
        }
        if(aggressor == null || victim == null){
            throw new IllegalArgumentException("Missing one or more entities");
        }
        this.aggresor = aggressor;
        this.victim = victim;
        this.lines = lines;
        this.mapView = map;
        textBox = new Rectangle(20,20, -1, -1);
        exlamationBox = new Rectangle(-1,-1, -1, -1);
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
        exlamationBox.setHeight(28f);
        exlamationBox.setWidth(12.5f);
    }

    @Override
    public void draw(GameData gameData, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        if(battleStarted)
            return;
        //Draw rectangle
        Camera cam = gameData.getCamera();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        textBox.draw(shapeRenderer, gameData.getDelta());

        Vector3 exlamationBoxPos = cam.project(new Vector3(victim.getPosX(), victim.getPosY(), 0f));
        exlamationBox.setY(exlamationBoxPos.y + 59);
        exlamationBox.setX(exlamationBoxPos.x + 12.5f);
        exlamationBox.draw(shapeRenderer, gameData.getDelta());

        shapeRenderer.end();

        //Animate triangle-thing

        //Draw text
        spriteBatch.setProjectionMatrix(gameData.getCamera().combined);
        spriteBatch.begin();
        if(victim.getType() == EntityType.GENERIC){
            TextUtils.getInstance().drawBigRoboto(spriteBatch,
                    "!",
                    Color.BLACK,
                    victim.getPosX() + 16,
                    victim.getPosY() + 85);
        }
        TextUtils.getInstance().drawNormalRoboto(spriteBatch,
                lines.peek(),
                Color.BLACK,
                (cam.position.x - cam.viewportWidth / 2f) + 25,
                (cam.position.y - cam.viewportHeight / 2f) + 100
        );
        spriteBatch.end();
    }

    @Override
    public void handleInput(GameData gameData) {
        if(gameData.getKeys().isPressed(GameKeys.ACTION)){
            this.lines.poll();
            if(lines.isEmpty()){
                mapView.startEncounter(aggresor, victim);
                battleStarted = true;
            }
        }
    }
}
