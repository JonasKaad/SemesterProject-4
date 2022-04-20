package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public interface IBattleAI {
    void doAction(IBattleSimulation battleSimulation);
    void opposingMonsterUsedMove(IMonster monster, IMonsterMove move);
}
