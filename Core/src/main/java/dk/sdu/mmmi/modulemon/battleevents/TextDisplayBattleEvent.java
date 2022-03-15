package dk.sdu.mmmi.modulemon.battleevents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;

public class TextDisplayBattleEvent implements IBattleEvent {
    private String _text;

    public TextDisplayBattleEvent(String text) {
        this._text = text;
    }

    @Override
    public String getText() {
        return _text;
    }
}
