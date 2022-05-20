package dk.sdu.mmmi.modulemon.SimpleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class BattleAI implements IBattleAI {

    private IBattleParticipant participantToControl;
    private IBattleSimulation battleSimulation;

    public BattleAI(IBattleSimulation battleSimulation, IBattleParticipant participantToControl) {
        this.participantToControl = participantToControl;
        this.battleSimulation = battleSimulation;
    }

    @Override
    public void doAction() {
        battleSimulation.doMove(participantToControl, participantToControl.getActiveMonster().getMoves().get(0));
    }

    @Override
    public void opposingMonsterUsedMove(IMonster monster, IMonsterMove move) {
    }
}
