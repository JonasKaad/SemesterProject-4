package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MovingPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.SpritePart;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IEntityProcessingService;

import static dk.sdu.mmmi.modulemon.common.data.GameKeys.*;

public class PlayerControlSystem implements IEntityProcessingService {

    String current = "up";

    @Override
    public void process(GameData gameData, World world) {
        if(gameData.isPaused())
            return;
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            SpritePart spritePart = player.getPart(SpritePart.class);
            MonsterTeamPart monsterTeamPart = player.getPart(MonsterTeamPart.class);

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

            movingPart.process(gameData, world, player);
            positionPart.process(gameData, world, player);
            spritePart.process(gameData, world, player);
            monsterTeamPart.process(gameData, world, player);

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
    }


}
