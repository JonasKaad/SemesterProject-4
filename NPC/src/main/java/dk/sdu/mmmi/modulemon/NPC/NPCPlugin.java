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
        PositionPart positionPart1 = new PositionPart((30)* 64 + 7, (64*63 - (46) * 64 ) + 20);
        npcs.get(0).add(new SpritePart(
                new Texture(new OSGiFileHandle("/assets/npc/npc1up.png", NPCPlugin.class)), //upSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc1down.png", NPCPlugin.class)), //downSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc1left.png", NPCPlugin.class)), //leftSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc1right.png", NPCPlugin.class)))); //rightSprite
        npcs.get(0).add(positionPart1);
        npcs.get(0).add(new MovingPart());
        npcs.get(0).add(new InteractPart(positionPart1, 5));
        npcs.get(0).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        npcs.get(0).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));


        npcs.add(1, new NPC());
        PositionPart positionPart2 = new PositionPart((55)* 64 + 7, (64*63 - (31) * 64 ) + 20);
        npcs.get(1).add(new SpritePart(
                new Texture(new OSGiFileHandle("/assets/npc/npc2up.png", NPCPlugin.class)), //upSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc2down.png", NPCPlugin.class)), //downSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc2left.png", NPCPlugin.class)), //leftSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc2right.png", NPCPlugin.class)))); //rightSprite
        npcs.get(1).add(positionPart2);
        npcs.get(1).add(new MovingPart());
        npcs.get(1).add(new InteractPart(positionPart2, 7));
        npcs.get(1).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        npcs.get(1).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));

        npcs.add(2, new NPC());
        PositionPart positionPart3 = new PositionPart((14)* 64 + 7, (64*63 - (14) * 64 ) + 20);
        npcs.get(2).add(new SpritePart(
                new Texture(new OSGiFileHandle("/assets/npc/npc3up.png", NPCPlugin.class)), //upSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc3down.png", NPCPlugin.class)), //downSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc3left.png", NPCPlugin.class)), //leftSprite
                new Texture(new OSGiFileHandle("/assets/npc/npc3right.png", NPCPlugin.class)))); //rightSprite
        npcs.get(2).add(positionPart3);
        npcs.get(2).add(new MovingPart());
        npcs.get(2).add(new InteractPart(positionPart3, 5));
        npcs.get(2).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        npcs.get(2).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));


        if(monsterRegistry != null) {
            addMonsterTeam(npcs.get(0), new int[]{0,1});
            addMonsterTeam(npcs.get(1), new int[]{3,5,4});
            addMonsterTeam(npcs.get(2), new int[]{3,2,5,1});
        }
    }
    public void addMonsterTeam(Entity entity, int[] ints) {
        List<IMonster> monsterList = new ArrayList<>();
        for (int i = 0; i < ints.length; i++) {
            monsterList.add(monsterRegistry.getMonster(ints[i]));
        }
        entity.add(new MonsterTeamPart(monsterList));
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
            for (int i = 0; i < npcs.size(); i++) {
                addMonsterTeam(npcs.get(i), new int[]{i});
            }
        }
    }

    public void removeMonsterRegistryService(IMonsterRegistry monsterRegistry) {
        this.monsterRegistry = null;
        if(npcs != null) for (Entity entity : npcs) {
            entity.remove(MonsterTeamPart.class);
        }
    }
}
