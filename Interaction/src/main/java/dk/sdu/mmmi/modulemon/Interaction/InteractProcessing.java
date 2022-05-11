package dk.sdu.mmmi.modulemon.Interaction;

import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.LinesPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityType;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.InteractPart;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IPostEntityProcessingService;

import java.util.LinkedList;
import java.util.Queue;

public class InteractProcessing implements IPostEntityProcessingService {
    private IMapView mapView;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            InteractPart interactPart = entity.getPart(InteractPart.class);
            if (interactPart != null) {
                if (interactPart.canInteract()) {
                    MonsterTeamPart monsterTeam1 = entity.getPart(MonsterTeamPart.class);
                    MonsterTeamPart monsterTeam2 = interactPart.getInteractWith().getPart(MonsterTeamPart.class);

                    // Check if one of the entities does not have a monster team
                    if (monsterTeam1 == null || monsterTeam2 == null) {
                        continue;
                    }

                    if (!monsterTeam1.hasAliveMonsters() || !monsterTeam2.hasAliveMonsters()) {
                        continue;
                    }

                    // This isn't great. Currently this relies on the NPC always initiating the battle.
                    // Could be nice to somehow check who is player, who is npc, and put them in the right spots.
                    if (entity.getType() == EntityType.PLAYER) {
                        Queue<String> lines = new LinkedList<>();
                        if(entity.getPart(LinesPart.class) != null){
                             lines = ((LinesPart) entity.getPart(LinesPart.class)).getLines();
                        }
                        BattleEvent battle = new BattleEvent(lines, entity, interactPart.getInteractWith(), mapView);
                        mapView.addMapEvent(battle);
                    }
                    else if (interactPart.getInteractWith().getType() == EntityType.PLAYER) {
                        Queue<String> lines = new LinkedList<>();
                        if(entity.getPart(LinesPart.class) != null){
                            lines = ((LinesPart) entity.getPart(LinesPart.class)).getLines();
                        }
                        BattleEvent battle = new BattleEvent(lines, interactPart.getInteractWith(), entity, mapView);
                        mapView.addMapEvent(battle);
                    }
                }
            }
        }
    }

    public void setMapView(IMapView mapView) {
        this.mapView = mapView;
    }

    public void removeMapView(IMapView mapView) {
        this.mapView = null;
    }
}
