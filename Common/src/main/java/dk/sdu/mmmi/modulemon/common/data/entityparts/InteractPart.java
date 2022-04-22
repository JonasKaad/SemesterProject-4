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
    private boolean interact;
    private PositionPart positionPart;
    private int tileSize = 64;
    private int range;
    private static final List<InteractPart> InteractPartList = new LinkedList();



    public InteractPart(PositionPart positionPart, int range) {
        this.interact = false;
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
    
    public boolean isInteract() {
        return interact;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (InteractPartList.size() < 2) return; 
        for (InteractPart interactPart : InteractPartList) {
            if (this == interactPart) {
                return;
            }
            interact = this.isInRange(interactPart.positionPart.getX(), interactPart.positionPart.getY());            
        }
    }
    
    private boolean isInRange(float x, float y) {
        boolean isInRange = false;
        
        if (this.positionPart.getX()*tileSize < x*tileSize 
        && this.positionPart.getX()*tileSize + this.range*tileSize >= x*tileSize
        && this.positionPart.getY()*tileSize > y*tileSize-32
        && this.positionPart.getY()*tileSize < y*tileSize+32
        //&& this.positionPart.isFacing('R')
                ){
            isInRange = true;
        } 
        else if (this.positionPart.getX()*tileSize > x*tileSize 
        && this.positionPart.getX()*tileSize - this.range*tileSize <= x*tileSize
        && this.positionPart.getY()*tileSize > y*tileSize-32
        && this.positionPart.getY()*tileSize < y*tileSize+32
        //&& this.positionPart.isFacing('L')
                ){
            isInRange = true;
        } 
        else if (this.positionPart.getY()*tileSize < y*tileSize 
        && this.positionPart.getY()*tileSize + this.range*tileSize >= y*tileSize
        && this.positionPart.getX()*tileSize > x*tileSize-32
        && this.positionPart.getX()*tileSize < x*tileSize+32
        //&& this.positionPart.isFacing('U')
                ){
            isInRange = true;   
        }
        else if (this.positionPart.getY()*tileSize > y*tileSize 
        && this.positionPart.getY()*tileSize - this.range*tileSize <= y*tileSize
        && this.positionPart.getX()*tileSize > x*tileSize-32
        && this.positionPart.getX()*tileSize < x*tileSize+32   
        //&& this.positionPart.isFacing('D')
                ){
            isInRange = true;            
        }
        if (isInRange) {
            System.out.println("isInRange: " + isInRange);
        }
        return isInRange;
    }
}   
