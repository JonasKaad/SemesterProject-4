/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.*;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IGamePluginService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gorm Krings
 */
public class NPCPlugin implements IGamePluginService{
    
    ArrayList<Entity> npcs = new ArrayList<>();
    IMonsterRegistry monsterRegistry;

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("NPCPlugin start");
        npcs.add(createNPC());
                
        for (Entity npc : npcs) {
            world.addEntity(npc);
        }
    }
    
    private Entity createNPC() {
        System.out.println("createNPC()");
        PositionPart positionPart = new PositionPart(3014, 1984);

        List<IMonster> monsterList = new ArrayList<>();
        monsterList.add(monsterRegistry.getMonster(0));
        monsterList.add(monsterRegistry.getMonster(3));

        Entity npc = new NPC(
                "John", 
                new SpritePart(
                        new Texture(new OSGiFileHandle("/assets/npc-char-up.png", NPCPlugin.class)), //upSprite 
                        new Texture(new OSGiFileHandle("/assets/npc-char-down.png", NPCPlugin.class)), //downSprite
                        new Texture(new OSGiFileHandle("/assets/npc-char-left.png", NPCPlugin.class)), //leftSprite
                        new Texture(new OSGiFileHandle("/assets/npc-char-right.png", NPCPlugin.class))),//rightSprite
                positionPart,
                new MovingPart(),
                new InteractPart(positionPart, 5),
                new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}),
                new MonsterTeamPart(monsterList));
        return npc;
    }

    @Override
    public void stop(GameData gameData, World world) {
        System.out.println("NPCPlugin stop");
        for (Entity npc : npcs){
            if (npc.getClass() == NPC.class) {
                world.removeEntity(npc);
            }
        }
    }

    public void setMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = monsterRegistry;
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
    }
    
}
