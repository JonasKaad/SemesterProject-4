package dk.sdu.mmmi.modulemon.CommonBattleClient;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

import java.util.List;

public interface IBattleView {
    // The init method should take the participants as arguments and create a BattleSimulation
    // I don't know if this is right, but that's how the interfaces are set up now
    void startBattle(List<IMonster> playerMonsters, List<IMonster> enemyMonsters, IBattleCallback callback);
    IGameViewService getGameView();
    void forceBattleEnd();
}
