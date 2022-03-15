package dk.sdu.mmmi.modulemon.battleview;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

import java.util.List;

public class MockPlayer implements IBattleParticipant {
    @Override
    public boolean isPlayerControlled() {
        return true;
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
