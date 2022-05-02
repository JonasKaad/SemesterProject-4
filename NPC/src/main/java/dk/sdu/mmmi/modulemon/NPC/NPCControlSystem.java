/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonBattle.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.MovingPart;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
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
    
    private String current = "";
    private IMapView mapView;

    @Override
    public void process(GameData gameData, World world) {

            for (Entity npc : world.getEntities(NPC.class)) {
                PositionPart positionPart = npc.getPart(PositionPart.class);
                MovingPart movingPart = npc.getPart(MovingPart.class);
                SpritePart spritePart = npc.getPart(SpritePart.class);
                AIControlPart controlPart = npc.getPart(AIControlPart.class);
                InteractPart interactPart = npc.getPart(InteractPart.class);
                
                movingPart.setLeft(controlPart.goLeft());
                movingPart.setRight(controlPart.goRight());
                movingPart.setUp(controlPart.goUp());
                movingPart.setDown(controlPart.goDown());
                // else stand still
                
                if(controlPart.goLeft()){
                    current = "left";
                }
                if(controlPart.goRight()){
                    current = "right";
                }
                if(controlPart.goUp()){
                    current = "up";
                }
                if(controlPart.goDown()){
                    current = "down";
                }
                
                movingPart.process(gameData, world, npc);
                positionPart.process(gameData, world, npc);
                spritePart.process(gameData, world, npc);
                controlPart.process(gameData, world, npc);
                interactPart.process(gameData, world, npc);
                
                if (interactPart.canInteract()) {
                    if (interactPart.getInteractWith().getPart(MonsterTeamPart.class) == null || npc.getPart(MonsterTeamPart.class) == null) {
                        continue;
                    }
                    mapView.startEncounter(interactPart.getInteractWith(), npc);
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
            default: System.out.println(("Did not match any direction"));
        }
        

        entity.setSpriteTexture(result);
        entity.setPosX(x);
        entity.setPosY(y);
    }

    public void setMapView(IMapView mapView) {
        this.mapView = mapView;
        System.out.println("INJECTED MAPVIEW IN NPC");
        System.out.println(mapView);
    }

    public void removeMapView(IMapView mapView) {
        this.mapView = null;
        System.out.println("MapView removed from NPCControlSystem");
    }
    
}
