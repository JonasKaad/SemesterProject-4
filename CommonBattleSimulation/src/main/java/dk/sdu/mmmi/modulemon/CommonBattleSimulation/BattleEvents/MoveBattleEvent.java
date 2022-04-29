package dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleState;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class MoveBattleEvent implements IBattleEvent {

    private String text;
    private IBattleParticipant usingParticipant;
    private IMonsterMove move;
    private int damage;
    private IBattleState battleState;

    public MoveBattleEvent(String text, IBattleParticipant usingParticipant, IMonsterMove move, int damage, IBattleState state) {
        this.text = text;
        this.usingParticipant = usingParticipant;
        this.move = move;
        this.damage = damage;
        this.battleState = state;
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

    @Override
    public IBattleState getState() {
        return this.battleState;
    }

    public int getDamage() {return damage;}
}
