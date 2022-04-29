/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.common.data.Direction;
import static dk.sdu.mmmi.modulemon.common.data.Direction.*;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Gorm
 */
public class InteractPart implements EntityPart{
    private boolean canInteract;
    private PositionPart positionPart;
    private int range;
    private static final List<InteractPart> InteractPartList = new LinkedList();
    
    public InteractPart(PositionPart positionPart, int range) {
        this.canInteract = false;
        this.positionPart = positionPart;
        this.range = range;
        
        InteractPartList.add(this);
    }

    public void setPositionPart(PositionPart positionPart) {
        this.positionPart = positionPart;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public boolean canInteract() {
        return canInteract;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (InteractPartList.size() < 2) return; 
        for (InteractPart interactPart : InteractPartList) {
            if (this == interactPart) {
                return;
            }
            canInteract = this.isInRange(interactPart.positionPart.getX(), interactPart.positionPart.getY());            
        }
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
}   
