package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IEntityProcessingService;

import java.util.Collection;

import static dk.sdu.mmmi.modulemon.common.data.GameKeys.*;

public class PlayerControlSystem implements IEntityProcessingService {

    private String current = "down";

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            MovingPart movingPart = player.getPart(MovingPart.class);

            if (movingPart != null) {
                movingPart.setLeft(gameData.getKeys().isDown(LEFT));
                movingPart.setRight(gameData.getKeys().isDown(RIGHT));
                movingPart.setUp(gameData.getKeys().isDown(UP));
                movingPart.setDown(gameData.getKeys().isDown(DOWN));

                if(gameData.getKeys().isDown(LEFT)){
                    current = "left";
                }
                if(gameData.getKeys().isDown(RIGHT)){
                    current = "right";
                }
                if(gameData.getKeys().isDown(UP)){
                    current = "up";
                }
                if(gameData.getKeys().isDown(DOWN)){
                    current = "down";
                }
            }

            Collection<EntityPart> entityParts = player.getParts();
            for (EntityPart entityPart : entityParts) {
                entityPart.process(gameData, world, player);
            }

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        SpritePart spritePart = entity.getPart(SpritePart.class);


        Texture result = null;
        switch (current) {
            case "right":
               result = spritePart.getRightSprite();
               break;
            case "left":
                result = spritePart.getLeftSprite();
                break;
            case "up":
                result = spritePart.getUpSprite();
                break;
            case "down":
                result = spritePart.getDownSprite();
                break;
            default: System.out.println(("Did not match any direction"));
        }

        spritePart.setCurrentSprite(result);
        //entity.setSpriteTexture(result);
        positionPart.setX(x);
        positionPart.setY(y);
    }


}
