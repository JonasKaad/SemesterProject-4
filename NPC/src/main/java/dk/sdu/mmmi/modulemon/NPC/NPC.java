/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityType;

/**
 *
 * @author Gorm Krings
 */
public class NPC extends Entity {
    public NPC(){
        super(EntityType.GENERIC);
    }

    
    public NPC(String name, SpritePart sprites, PositionPart position, MovingPart movement, InteractPart interact, AIControlPart control, MonsterTeamPart monsterTeamPart) {
        this.add(sprites);
        this.add(position);
        this.add(movement);
        this.add(interact);
        this.add(control);
        this.add(monsterTeamPart);
    }
}
