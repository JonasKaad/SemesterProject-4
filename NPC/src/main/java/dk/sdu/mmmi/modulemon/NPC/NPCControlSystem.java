/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;
import dk.sdu.mmmi.modulemon.common.services.IEntityProcessingService;

/**
 *
 * @author Gorm Krings
 */
public class NPCControlSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {

            for (Entity npc : world.getEntities(NPC.class)) {
                PositionPart positionPart = npc.getPart(PositionPart.class);
                MovingPart movingPart = npc.getPart(MovingPart.class);
                SpritePart spritePart = npc.getPart(SpritePart.class);
                AIControlPart controlPart = npc.getPart(AIControlPart.class);
                InteractPart interactPart = npc.getPart(InteractPart.class);
                
                movingPart.setLeft(controlPart.shouldGoLeft());
                movingPart.setRight(controlPart.shouldGoRight());
                movingPart.setUp(controlPart.shouldGoUp());
                movingPart.setDown(controlPart.shouldGoDown());
                // else stand still
                String current = "";
                
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
                
                movingPart.process(gameData, world, npc);
                positionPart.process(gameData, world, npc);
                spritePart.process(gameData, world, npc);
                controlPart.process(gameData, world, npc);
                interactPart.process(gameData, world, npc);
                
                if (interactPart.canInteract()) {

                } 

                updateShape(npc, current);
        }
    }

    private void updateShape(Entity entity, String current) {

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
        

        entity.setSpriteTexture(result);
        entity.setPosX(x);
        entity.setPosY(y);

        //entity.setSprite(new Texture(new OSGiFileHandle("/assets/main-char-right.png")));
    }
    
}
