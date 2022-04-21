/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonNPC.INPC;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;

/**
 *
 * @author Gorm Krings
 */
public class NPC extends Entity implements INPC {
    
    private String name;
    private SpritePart sprites; //4 sprites
    private PositionPart position;
    private MovingPart movement;
    private InteractPart interact;
    private AIControlPart control;
    private int facingDirection; //posible ints [0, 90, 180, 270]
    private String dialog; 

    
    public NPC(String name, SpritePart sprites, PositionPart position, MovingPart movement, InteractPart interact, AIControlPart control, int facingDirection) {
        this.name = name;
        this.sprites = sprites;
        this.position = position;
        this.movement = movement;
        this.interact = interact;
        this.control = control;
        this.facingDirection = facingDirection;
        
        this.add(sprites);
        this.add(position);
        this.add(movement);
        this.add(interact);
        this.add(control);
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
        System.out.println("Interact");
    }
    
}
