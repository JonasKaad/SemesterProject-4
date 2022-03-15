package dk.sdu.mmmi.modulemon.battleevents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;

public class VictoryBattleEvent implements IBattleEvent {
    private String _victoryText;

    public VictoryBattleEvent(){
        this("You won, lol");
    }

    public VictoryBattleEvent(String _victoryText) {
        this._victoryText = _victoryText;
    }

    @Override
    public String getText() {
        return _victoryText;
    }
}
