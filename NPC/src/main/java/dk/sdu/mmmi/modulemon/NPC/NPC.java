/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;

/**
 *
 * @author Gorm Krings
 */
public class NPC extends Entity implements INPC {
    
    private String name;
    private Texture sprite;
    private int facingDirection; //posible ints [0, 90, 180, 270]
    //private PositionPart position;

    public NPC(String name, Texture sprite, int facingDirection) {
        this.name = name;
        this.sprite = sprite;
        this.facingDirection = facingDirection;
    }
    
    @Override
    public void turnClockwise() {
        facingDirection = (facingDirection+90)%360;
    }

    @Override
    public void turnCounterClockwise() {
        facingDirection = (facingDirection-90)%360;

    }

    @Override
    public void takeStep() {
        
    }

    @Override
    public void interact() {
    }
    
}
