/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import dk.sdu.mmmi.modulemon.CommonBattle.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.MovingPart;
import dk.sdu.mmmi.modulemon.CommonNPC.INPC;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;

/**
 *
 * @author Gorm Krings
 */
public class NPC extends Entity implements INPC {

    
    public NPC(String name, SpritePart sprites, PositionPart position, MovingPart movement, InteractPart interact, AIControlPart control, MonsterTeamPart monsterTeamPart) {
        this.add(sprites);
        this.add(position);
        this.add(movement);
        this.add(interact);
        this.add(control);
        this.add(monsterTeamPart);
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
