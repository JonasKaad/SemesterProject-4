package dk.sdu.mmmi.modulemon.Player;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonBattle.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.*;
import dk.sdu.mmmi.modulemon.common.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    private static List<IMonsterRegistry> monsterRegistryList = new CopyOnWriteArrayList<>();

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {

        float x =  3014;
        float y =  1984;

        Entity player = new Player();
        player.add(new PositionPart(x, y));
        player.add(new MovingPart());
        Texture upSprite = new Texture(new OSGiFileHandle("/assets/main-char-up5.png", Player.class));
        Texture downSprite = new Texture(new OSGiFileHandle("/assets/main-char-down5.png", Player.class));
        Texture leftSprite = new Texture(new OSGiFileHandle("/assets/main-char-left5.png", Player.class));
        Texture rightSprite = new Texture(new OSGiFileHandle("/assets/main-char-right5.png", Player.class));
        player.add(new SpritePart(upSprite, downSprite, leftSprite, rightSprite));
        IMonsterRegistry monsterRegistry = monsterRegistryList.get(0);

        List<IMonster> monsterList = new ArrayList<>();
        monsterList.add(monsterRegistry.getMonster(0));
        monsterList.add(monsterRegistry.getMonster(1));
        player.add(new MonsterTeamPart(monsterList));

        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }


    public void addMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistryList.add(registry);
    }

    // Used for tests
    public void setMonsterRegistryList(List<IMonsterRegistry> monsterRegistryList) {
        this.monsterRegistryList = monsterRegistryList;
    }

    public void removeMonsterRegistryService (IMonsterRegistry registry){
        this.monsterRegistryList.remove(registry);
    }

}
