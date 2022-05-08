/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import static dk.sdu.mmmi.modulemon.CommonMap.Data.Direction.*;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Direction;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;

/**
 *
 * @author Gorm
 */
public class InteractPart implements EntityPart {
    private Entity interactWith;
    private PositionPart positionPart;
    private int range;
    
    public InteractPart(PositionPart positionPart, int range) {
        this.positionPart = positionPart;
        this.range = range;
    }

    public void setPositionPart(PositionPart positionPart) {
        this.positionPart = positionPart;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public boolean canInteract() {
        return this.interactWith != null;
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {
        for (Entity e : world.getEntities()){
            InteractPart interactPart = e.getPart(InteractPart.class);
            if(interactPart == null || interactPart == this){
                continue;
            }

            boolean canInteract = this.isInRange(interactPart.positionPart.getX(), interactPart.positionPart.getY());
            if(canInteract){
                this.interactWith = e;
                return;
            }
        }

        //If there we no entities to interact with, set to null
        this.interactWith = null;
    }
    
    private boolean isInRange(float x, float y) {
        float thisX = this.positionPart.getX();
        float thisY = this.positionPart.getY();
        Direction direction = this.positionPart.getDirection();

        boolean bothHaveSameX = (x-32 < thisX && thisX < x+32);
        boolean bothHaveSameY = (y-32 < thisY && thisY < y+32);
        boolean withinRange = (Math.abs(thisX-x) <= 64*range) && (Math.abs(thisY-y) <= 64*range);
        
        //If they have one of the same values of X or Y axises,
        //They are within a range of tiles of each other,
        //Is facing the other interactpart.
        if (bothHaveSameX && withinRange && ((thisY < y && direction == NORTH) || (thisY > y && direction == SOUTH))) {
            return true;
        } 
        else if (bothHaveSameY && withinRange && ((thisX < x && direction == EAST) || (thisX > x && direction == WEST))) {
            return true;    
        }
        
        return false;
    }

    public Entity getInteractWith() {
        return interactWith;
    }
}   
