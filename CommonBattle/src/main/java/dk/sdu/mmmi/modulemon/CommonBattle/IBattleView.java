package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

public interface IBattleView {
    // The init method should take the participants as arguments and create a BattleSimulation
    // I don't know if this is right, but that's how the interfaces are set up now
    void startBattle(IBattleParticipant Player, IBattleParticipant Enemy, IBattleCallback callback);
    IGameViewService getGameView();
    void forceBattleEnd();
}
