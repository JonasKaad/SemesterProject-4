package dk.sdu.mmmi.modulemon.MapEntities;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.TextMapEvent;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class HealingMachineEvent extends TextMapEvent {
    private Entity interactee;

    public HealingMachineEvent(Entity interactee) {
        super(new LinkedList<>(Arrays.asList("You use the healing machine to heal your monsters.")));
        if(interactee == null){
            throw new IllegalArgumentException("interactee is null");
        }
        this.interactee = interactee;
    }

    @Override
    public void handleInput(GameData gameData) {
        super.handleInput(gameData);
        if(super.lines.isEmpty()){
            // Do the healing
            MonsterTeamPart monsterTeamPart = this.interactee.getPart(MonsterTeamPart.class);
            if(monsterTeamPart != null){
                monsterTeamPart.healAllMonsters();
            }
        }
    }
}
