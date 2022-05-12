package dk.sdu.mmmi.modulemon.MapEntities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.IMapEvent;
import dk.sdu.mmmi.modulemon.common.data.GameData;

public class VendingMachineEvent implements IMapEvent {
    private Entity entity;
    public VendingMachineEvent(Entity entity){
        if(entity == null){
            throw new IllegalArgumentException("entity is null");
        }
        this.entity = entity;
    }

    @Override
    public boolean isEventDone() {
        return true;
    }

    @Override
    public void start(GameData gameData) {
    }

    @Override
    public void update(GameData gameData) {
        System.out.println("Burde gøre et vending-machine event.");
    }

    @Override
    public void draw(GameData gameData, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public void handleInput(GameData gameData) {

    }
}
