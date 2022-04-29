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

    
    public NPC(String name, SpritePart sprites, PositionPart position, MovingPart movement, InteractPart interact, AIControlPart control) {
        this.add(sprites);
        this.add(position);
        this.add(movement);
        this.add(interact);
        this.add(control);
    }
    
    @Override
    public void turnClockwise() {
    }

    @Override
    public void turnCounterClockwise() {

    }

    @Override
    public void takeStep() {
        
    }

    @Override
    public void interact() {
        System.out.println("Interact");
    }
    
}
