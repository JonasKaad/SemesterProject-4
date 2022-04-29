/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

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
        boolean isInRange = false;
        float thisX = this.positionPart.getX();
        float thisY = this.positionPart.getY();
        int direction = this.positionPart.getDirection();

        boolean bothOnAxisX = (x-32 < thisX && thisX < x+32);
        boolean bothOnAxisY = (y-32 < thisY && thisY < y+32);
        boolean withinRange = (Math.abs(thisX-x) <= 64*range) && (Math.abs(thisY-y) <= 64*range);
        
        //If they are on one of the same axises
        //They are within a range of tiles of each other
        //Is facing the other interactpart.
        if (bothOnAxisX && withinRange) {
            if (thisY < y && direction == 90) {
                
            } else if (thisY > y && direction == 270) {
                
            } else {
                return isInRange;
            }
        } 
        else if (bothOnAxisY && withinRange) {
            if (thisX < x && direction == 0) {
                
            } else if (thisX > x && direction == 180) {
                
            } else {
                return isInRange;
            }
            
        } else {
            return isInRange;
        }
        isInRange = true;
        
        return isInRange;
    }
}   
