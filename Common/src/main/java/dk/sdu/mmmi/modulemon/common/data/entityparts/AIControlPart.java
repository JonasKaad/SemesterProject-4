/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;

/**
 *
 * @author Gorm
 */
public class AIControlPart implements EntityPart{
    
    private Character[] movementArray;
    private int index;
    private Character current;
    private float movingTimer = 0;
    
    public AIControlPart(Character[] movementArray) {
        this.movementArray = movementArray;
        this.index = 0;
        this.current = movementArray[index];
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {
        float dt = gameData.getDelta(); // Time in seconds between frames
        
        if (movingTimer > 0) {
            current = 'S';
            movingTimer -= dt;
        } else {
            updateMovement();
            movingTimer = 1.5f + (float) Math.random();
        }
    }
    
    private void updateMovement() {
        index = (index + 1) % movementArray.length;
        current = movementArray[index];
        //System.out.println(current);
    }
    
    public Character getMovement() {
        return current;
    }
    
    public boolean goLeft() {
        return current == 'L';
    }
    
    public boolean goRight() {
        return current == 'R';
    }
    
    public boolean goUp() {
        return current == 'U';
    }
    
    public boolean goDown() {
        return current == 'D';        
    }


}
