/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
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
    
    List<Entity> npcs;
    IMonsterRegistry monsterRegistry;

    @Override
    public void start(GameData gameData, World world) {
        createNPCs();
        for (Entity npc : npcs) {
            world.addEntity(npc);
        }
    }
    
    private void createNPCs() {
        npcs = new ArrayList<>();

        npcs.add(0, new NPC());
        PositionPart positionPart = new PositionPart(3014, 1984);
        npcs.get(0).add(new SpritePart(
                new Texture(new OSGiFileHandle("/assets/npc-char-up.png", NPCPlugin.class)), //upSprite
                new Texture(new OSGiFileHandle("/assets/npc-char-down.png", NPCPlugin.class)), //downSprite
                new Texture(new OSGiFileHandle("/assets/npc-char-left.png", NPCPlugin.class)), //leftSprite
                new Texture(new OSGiFileHandle("/assets/npc-char-right.png", NPCPlugin.class))));
        npcs.get(0).add(positionPart);
        npcs.get(0).add(new MovingPart());
        npcs.get(0).add(new InteractPart(positionPart, 5));
        npcs.get(0).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        npcs.get(0).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        addMonsterTeam(npcs);
    }

    public void addMonsterTeam(List<Entity> entities) {
        List<IMonster> monsterList = new ArrayList<>();
        monsterList.add(monsterRegistry.getMonster(0));
        monsterList.add(monsterRegistry.getMonster(3));
        entities.get(0).add(new MonsterTeamPart(monsterList));
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
        if (npcs != null) {
            addMonsterTeam(npcs);
        }
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
        for (Entity entity : npcs) {
            entity.remove(MonsterTeamPart.class);
        }
    }
    
}
