/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

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
                
                //Implement moving for each npc, how?
                
                double randomDouble = Math.random();
                boolean goLeft = false;
                boolean goRight = false;
                boolean goUp = false;
                boolean goDown = false;
                
                if (randomDouble < 0.25) {
                    goLeft = true;
                } else if (randomDouble >= 0.25 && randomDouble < 0.5) {
                    goRight = true;
                } else if (randomDouble >= 0.5 && randomDouble < 0.75) {
                    goUp = true;
                } else {
                    goDown = true;
                }
                
                movingPart.setLeft(goLeft);
                movingPart.setRight(goRight);
                movingPart.setUp(goUp);
                movingPart.setDown(goDown);
                
                movingPart.process(gameData, npc);
                positionPart.process(gameData, npc);
                spritePart.process(gameData, npc);

                updateShape(npc);
        }
    }

    private void updateShape(Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        SpritePart spritePart = entity.getPart(SpritePart.class);


        

        entity.setSpriteString("/assets/npc.png");
        entity.setPosX(x);
        entity.setPosY(y);

        //entity.setSprite(new Texture(new OSGiFileHandle("/assets/main-char-right.png")));
    }
    
}
