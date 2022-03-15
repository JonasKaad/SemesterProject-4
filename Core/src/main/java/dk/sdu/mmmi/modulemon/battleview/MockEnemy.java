package dk.sdu.mmmi.modulemon.battleview;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

import java.util.List;

public class MockEnemy implements IBattleParticipant {
    @Override
    public boolean isPlayerControlled() {
        return false;
    }

    @Override
    public IMonster getActiveMonster() {
        return null;
    }

    @Override
    public void setActiveMonster(IMonster monster) {

    }

    @Override
    public List<IMonster> getMonsterTeam() {
        return null;
    }
}
