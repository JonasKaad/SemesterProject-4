package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

public class EmptyMove implements IMonsterMove {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public MonsterType getType() {
        return null;
    }

    @Override
    public String getSoundPath() {
        return null;
    }
}
