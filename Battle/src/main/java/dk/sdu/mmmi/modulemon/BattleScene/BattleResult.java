package dk.sdu.mmmi.modulemon.BattleScene;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleResult;

public class BattleResult implements IBattleResult {
    private IBattleParticipant winner;
    private IBattleParticipant player;
    private IBattleParticipant enemy;

    public BattleResult(IBattleParticipant winner, IBattleParticipant player, IBattleParticipant enemy) {
        this.winner = winner;
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public IBattleParticipant getWinner() {
        return winner;
    }

    @Override
    public IBattleParticipant getPlayer() {
        return player;
    }

    @Override
    public IBattleParticipant getEnemy() {
        return enemy;
    }
}
