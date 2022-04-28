package dk.sdu.mmmi.modulemon.CommonBattleSimulation;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleMonsterProcessor {
    IMonster whichMonsterStarts(IMonster monster1, IMonster monster2);
    int calculateDamage(IMonster source, IMonsterMove move, IMonster target);
}