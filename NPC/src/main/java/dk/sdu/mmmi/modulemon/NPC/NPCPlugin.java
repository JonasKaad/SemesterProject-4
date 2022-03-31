/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import dk.sdu.mmmi.modulemon.BattleScene.OSGiFileHandle;
import java.util.ArrayList;

/**
 *
 * @author Gorm Krings
 */
public class NPCPlugin implements IGamePluginService{
    
    ArrayList<INPC> npcs = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("NPCPlugin start");
        Entity npc = new NPC("John", new Texture(new OSGiFileHandle("/Sprites/npc.png")), 0);
        world.addEntity(npc);
    }

    @Override
    public void stop(GameData gameData, World world) {
        System.out.println("NPCPlugin stop");
        for (Entity npc : world.getEntities()){
            if (npc.getClass() == NPC.class) {
                world.removeEntity(npc);
            }
        }
    }
    
}
