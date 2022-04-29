/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Gorm
 */
public class InteractPart implements EntityPart{
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

        //If there we no entiies to interact with, set to null
        this.interactWith = null;
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

    public Entity getInteractWith() {
        return interactWith;
    }
}   
