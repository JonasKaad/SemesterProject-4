package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.entityparts.EntityPart;

import java.util.ArrayList;
import java.util.List;

public class MonsterTeamPart implements EntityPart {

    List<IMonster> monsterTeam = new ArrayList<>();


    public MonsterTeamPart(List<IMonster> monsterTeam) {
        this.monsterTeam = monsterTeam;
    }

    public List<IMonster> getMonsterTeam() {
        return monsterTeam;
    }

    public void setMonsterTeam(List<IMonster> monsterTeam) {
        this.monsterTeam = monsterTeam;
    }

    public void printMonsterTeamNames(){
        for (IMonster monster: monsterTeam){
            System.out.println(monster.getName());
        }
    }

    public IBattleParticipant toBattleParticipant(boolean playerControlled){
        IBattleParticipant battleParticipant = new BattleParticipant(getMonsterTeam(), playerControlled);
        return battleParticipant;
    }


    @Override
    public void process(GameData gameData, Entity entity) {

    }
}