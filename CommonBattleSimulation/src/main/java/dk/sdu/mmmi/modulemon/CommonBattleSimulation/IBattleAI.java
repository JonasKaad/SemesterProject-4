package dk.sdu.mmmi.modulemon.CommonBattleSimulation;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleAI {
    void doAction();
    void opposingMonsterUsedMove(IMonster monster, IMonsterMove move);
}
