/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.modulemon.NPC;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;
import dk.sdu.mmmi.modulemon.common.drawing.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;
import java.util.ArrayList;

/**
 *
 * @author Gorm Krings
 */
public class NPCPlugin implements IGamePluginService{
    
    ArrayList<Entity> npcs = new ArrayList<>();

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
        Entity npc = new NPC(
                "John", 
                new SpritePart(
                        new Texture(new OSGiFileHandle("/assets/npc-char-up.png", NPCPlugin.class)), //upSprite 
                        new Texture(new OSGiFileHandle("/assets/npc-char-down.png", NPCPlugin.class)), //downSprite
                        new Texture(new OSGiFileHandle("/assets/npc-char-left.png", NPCPlugin.class)), //leftSprite
                        new Texture(new OSGiFileHandle("/assets/npc-char-right.png", NPCPlugin.class))),//rightSprite
                new PositionPart(3008, 2048),
                new MovingPart(),
                new InteractPart(),
                new AIControlPart(new Character[]{'R','R','L','L','L','L','R','R','U','U','D','D','D','D','U','U'}), //?(??_?)??
                0);
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
    
}
