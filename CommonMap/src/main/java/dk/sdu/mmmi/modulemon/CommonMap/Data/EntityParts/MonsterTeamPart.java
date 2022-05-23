package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.List;

public class MonsterTeamPart implements EntityPart {

    private List<IMonster> monsterTeam;


    public MonsterTeamPart(List<IMonster> monsterTeam) {
        this.monsterTeam = monsterTeam;
    }

    public List<IMonster> getMonsterTeam() {
        return monsterTeam;
    }

    public void setMonsterTeam(List<IMonster> monsterTeam) {
        this.monsterTeam = monsterTeam;
    }

    public boolean hasAliveMonsters(){
        for (IMonster monster: monsterTeam) {
            if(monster.getHitPoints() > 0)
                return true;
        }
        return false;
    }

    public void healAllMonsters(){
        monsterTeam.forEach(x -> x.setHitPoints(x.getMaxHitPoints()));
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}