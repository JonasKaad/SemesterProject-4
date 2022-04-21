/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
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
    private static final List<InteractPart> InteractPartList = new LinkedList();
    private static IMapView mapView;
    private int tileSize = mapView.getTileSize();
    private final int Range;


    public InteractPart(PositionPart positionPart, int Range) {
        this.interact = false;
        this.positionPart = positionPart;
        this.Range = Range;
        
        InteractPartList.add(this);
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        for (InteractPart interactPart : InteractPartList) {
            if (this != interactPart) {
                this.shouldInteract(interactPart);
            }
        }
    }
    
    //Check line of sight for player in 5 tiles.
    private boolean shouldInteract(InteractPart interactPart) {
        boolean shouldInteract = false;
        if (isInRange(interactPart.positionPart.getX(), interactPart.positionPart.getY())) {
            shouldInteract = true;
        } 
        return shouldInteract;
    }
    
    private boolean isInRange(float x, float y) {
        boolean isInRange = false;
        
        if (this.positionPart.getX()*tileSize < x*tileSize 
        && this.positionPart.getX()*tileSize + this.Range*tileSize > x*tileSize
        && this.positionPart.getY()*tileSize == y*tileSize
        && positionPart.isFacing('R')){
            isInRange = true;
        } 
        else if (this.positionPart.getX()*tileSize < x*tileSize 
        && this.positionPart.getX()*tileSize + this.Range*tileSize > x*tileSize
        && this.positionPart.getY()*tileSize == y*tileSize
        && positionPart.isFacing('L')){
            isInRange = true;
        } 
        else if (this.positionPart.getY()*tileSize < y*tileSize 
        && this.positionPart.getY()*tileSize + this.Range*tileSize > y*tileSize
        && this.positionPart.getX()*tileSize == x*tileSize
        && positionPart.isFacing('U')) {
            isInRange = true;   
        }
        else if (this.positionPart.getY()*tileSize < y*tileSize 
        && this.positionPart.getY()*tileSize + this.Range*tileSize > y*tileSize
        && this.positionPart.getX()*tileSize == x*tileSize
        && positionPart.isFacing('D')) {
            isInRange = true;            
        }
        return isInRange;
    }

    public boolean isInteract() {
        return interact;
    }
    
    public void setMapView(IMapView mapView){
        while(InteractPart.mapView == null){
            InteractPart.mapView = mapView;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeMapView(IMapView mapView){
        InteractPart.mapView = null;
    }
}   
