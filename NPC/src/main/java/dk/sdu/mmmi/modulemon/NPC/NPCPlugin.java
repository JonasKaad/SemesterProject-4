/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.*;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IGamePluginService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NPCPlugin implements IGamePluginService{
    
    private List<Entity> npcs;
    private IMonsterRegistry monsterRegistry;
    private int[] team0 = new int[]{5};
    private int[] team1 = new int[]{0, 3};
    private int[] team2 = new int[]{3, 2, 4, 1};

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
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc1up.png", NPCPlugin.class), //upSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc1down.png", NPCPlugin.class), //downSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc1left.png", NPCPlugin.class), //leftSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc1right.png", NPCPlugin.class))); //rightSprite
        npcs.get(0).add(positionPart1);
        npcs.get(0).add(new MovingPart());
        npcs.get(0).add(new InteractPart(positionPart1, 5));
        npcs.get(0).add(new AIControlPart(new Character[]{'U','U','U','D','D','D','D','D','D','U','U','U','L','U','U','U','D','D','D','D','D','D','U','U','U','R'}));
        Queue<String> npc1Lines = new LinkedList<>();
        npc1Lines.add("Hey, you!");
        npc1Lines.add("Wanna battle?");
        npcs.get(0).add(new TextDisplayPart(npc1Lines));

        npcs.add(1, new NPC());
        PositionPart positionPart2 = new PositionPart((55)* 64 + 7, (64*63 - (31) * 64 ) + 20);
        npcs.get(1).add(new SpritePart(
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc2up.png", NPCPlugin.class), //upSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc2down.png", NPCPlugin.class), //downSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc2left.png", NPCPlugin.class), //leftSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc2right.png", NPCPlugin.class))); //rightSprite
        npcs.get(1).add(positionPart2);
        npcs.get(1).add(new MovingPart());
        npcs.get(1).add(new InteractPart(positionPart2, 7));
        npcs.get(1).add(new AIControlPart(new Character[]{'L','L','L','D','D','R','R','R','R','R','R','U','U','U','U','L','L','L','D','D'}));
        Queue<String> npc2Lines = new LinkedList<>();
        npc2Lines.add("Welcome to my swamp.");
        npc2Lines.add("Prepare to get put in the mud!");
        npcs.get(1).add(new TextDisplayPart(npc2Lines));

        npcs.add(2, new NPC());
        PositionPart positionPart3 = new PositionPart((14)* 64 + 7, (64*63 - (14) * 64 ) + 20);
        npcs.get(2).add(new SpritePart(
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc3up.png", NPCPlugin.class), //upSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc3down.png", NPCPlugin.class), //downSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc3left.png", NPCPlugin.class), //leftSprite
                AssetLoader.getInstance().getTextureAsset("/assets/npc/npc3right.png", NPCPlugin.class))); //rightSprite
        npcs.get(2).add(positionPart3);
        npcs.get(2).add(new MovingPart());
        npcs.get(2).add(new InteractPart(positionPart3, 5));
        npcs.get(2).add(new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}));
        Queue<String> npc3Lines = new LinkedList<>();
        npc3Lines.add("So, you made it all the way to me...");
        npc3Lines.add("Lets see if you can really battle!");
        npcs.get(2).add(new TextDisplayPart(npc3Lines));


        if(monsterRegistry != null) {
            addMonsterTeam(npcs.get(0), team0);
            addMonsterTeam(npcs.get(1), team1);
            addMonsterTeam(npcs.get(2), team2);
        }
    }
    public void addMonsterTeam(Entity entity, int[] ints) {
        List<IMonster> monsterList = new ArrayList<>();
        for (int i = 0; i < ints.length; i++) {
            monsterList.add(monsterRegistry.getMonster(ints[i] % monsterRegistry.getMonsterAmount()));
        }
        entity.add(new MonsterTeamPart(monsterList));
    }

    public int[] getTeam(String team){
        int[] real_team = new int[]{0};
        switch (team) {
            case "team0":
                real_team = team0;
                break;
            case "team1":
                real_team = team1;
                break;
            case "team2":
                real_team = team2;
                break;
        }
        return real_team;
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
                addMonsterTeam(npcs.get(i), getTeam("team" + i));
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
