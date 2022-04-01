package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.modulemon.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.modulemon.common.data.entityparts.SpritePart;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.modulemon.common.data.GameKeys.*;

/**
 *
 * @author jcs
 */
public class PlayerControlSystem implements IEntityProcessingService {

    String current = "up";

    @Override
    public void process(GameData gameData, World world) {



        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            SpritePart spritePart = player.getPart(SpritePart.class);

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

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            spritePart.process(gameData, player);

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

        entity.setSpriteTexture(result);
        entity.setPosX(x);
        entity.setPosY(y);

        //entity.setSprite(new Texture(new OSGiFileHandle("/assets/main-char-right.png")));
    }


}
