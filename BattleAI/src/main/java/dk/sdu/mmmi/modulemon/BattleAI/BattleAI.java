package dk.sdu.mmmi.modulemon.BattleAI;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;

public class BattleAI implements IBattleAI {

    @Override
    public void doAction(IBattleSimulation battleSimulation) {
        IBattleParticipant activeParticipant =
                battleSimulation.getState().isPlayersTurn()
                        ? battleSimulation.getState().getPlayer()
                        : battleSimulation.getState().getEnemy();
        battleSimulation.doMove(activeParticipant, activeParticipant.getActiveMonster().getMoves().get(0));
    }
}
