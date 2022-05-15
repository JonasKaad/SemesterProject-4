/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IEntityProcessingService;

import java.util.Collection;

/**
 *
 * @author Gorm Krings
 */
public class NPCControlSystem implements IEntityProcessingService{

    private String current = ""; // Save the current or it will be overrided to nothing.
    
    @Override
    public void process(GameData gameData, World world) {

            for (Entity npc : world.getEntities(NPC.class)) {
                MovingPart movingPart = npc.getPart(MovingPart.class);
                AIControlPart controlPart = npc.getPart(AIControlPart.class);

                if (movingPart != null && controlPart != null) {
                    movingPart.setLeft(controlPart.shouldGoLeft());
                    movingPart.setRight(controlPart.shouldGoRight());
                    movingPart.setUp(controlPart.shouldGoUp());
                    movingPart.setDown(controlPart.shouldGoDown());

                    // else stand still
                    if(controlPart.shouldGoLeft()){
                        current = "left";
                    }
                    if(controlPart.shouldGoRight()){
                        current = "right";
                    }
                    if(controlPart.shouldGoUp()){
                        current = "up";
                    }
                    if(controlPart.shouldGoDown()){
                        current = "down";
                    }
                }

                Collection<EntityPart> entityParts = npc.getParts();
                for (EntityPart entityPart : entityParts) {
                    entityPart.process(gameData, world, npc);
                }

                updateShape(npc);
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
            default: System.out.println(("The NPC sprite could not be loaded: Current did not match any direction"));
        }

        spritePart.setCurrentSprite(result);
        //entity.setSpriteTexture(result);
        positionPart.setX(x);
        positionPart.setY(y);
    }
    
}
