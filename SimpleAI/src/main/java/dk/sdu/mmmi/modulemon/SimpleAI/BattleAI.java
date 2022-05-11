package dk.sdu.mmmi.modulemon.SimpleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleAI implements IBattleAI {

    private IBattleParticipant participantToControl;

    public BattleAI(IBattleParticipant participantToControl) {
        this.participantToControl = participantToControl;
    }

    @Override
    public void doAction(IBattleSimulation battleSimulation) {
        battleSimulation.doMove(participantToControl, participantToControl.getActiveMonster().getMoves().get(0));
    }

    @Override
    public void opposingMonsterUsedMove(IMonster monster, IMonsterMove move) {
    }
}
