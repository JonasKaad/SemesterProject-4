package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class MoveBattleEvent implements IBattleEvent {

    private String text;
    private IBattleParticipant usingParticipant;
    private IMonsterMove move;
    private int damage;

    public MoveBattleEvent(String text, IBattleParticipant usingParticipant, IMonsterMove move, int damage) {
        this.text = text;
        this.usingParticipant = usingParticipant;
        this.move = move;
        this.damage = damage;
    }

    public IBattleParticipant getUsingParticipant() {
        return usingParticipant;
    }

    public IMonsterMove getMove() {
        return move;
    }

    @Override
    public String getText() {
        return text;
    }

    public int getDamage() {return damage;}
}
