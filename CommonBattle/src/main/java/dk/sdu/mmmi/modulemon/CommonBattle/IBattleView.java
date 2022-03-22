package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;

public interface IBattleView {
    // The init method should take the participants as arguments and create a BattleSimulation
    // I don't know if this is right, but that's how the interfaces are set up now
    void init(IBattleParticipant Player, IBattleParticipant Enemy);
    void dispose();
}
