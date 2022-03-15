package dk.sdu.mmmi.modulemon.battleview;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleEvent;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.battleevents.MonsterAttackMoveBattleEvent;
import dk.sdu.mmmi.modulemon.battleevents.MonsterSwitchBattleEvent;
import dk.sdu.mmmi.modulemon.battleevents.VictoryBattleEvent;

import java.util.Optional;
import java.util.PriorityQueue;

public class MockBattleSimulation implements IBattleSimulation {
    private IMonsterMove _defaultMove = MockMonsterMove.getDefaultMonsterMove();
    private IBattleParticipant _player;
    private IBattleParticipant _enemy;
    private PriorityQueue<IBattleEvent> _eventqueue;

    @Override
    public void StartBattle(IBattleParticipant player, IBattleParticipant enemy) {
        _player = player;
        _enemy = enemy;
        _eventqueue = new PriorityQueue<IBattleEvent>();
    }

    @Override
    public IBattleEvent getNextBattleEvent() {
        return _eventqueue.poll();
    }

    @Override
    public IBattleParticipant getPlayer() {
        return _player;
    }

    @Override
    public IBattleParticipant getEnemy() {
        return _enemy;
    }

    @Override
    public boolean isPlayersTurn() {
        return _eventqueue.isEmpty();
    }

    @Override
    public void doMove(IBattleParticipant battleParticipant, IMonsterMove move) {

        //Player does attack
        _eventqueue.add(new MonsterAttackMoveBattleEvent(_player.getActiveMonster(), _enemy.getActiveMonster(), move, _player));
        _enemy.getActiveMonster().setHitPoints(_enemy.getActiveMonster().getHitPoints() - move.getDamage());
        if (_enemy.getActiveMonster().getHitPoints() <= 0) {
            Optional<IMonster> newEnemyMonster = _enemy.getMonsterTeam().stream().filter(x -> x.getHitPoints() > 0).findFirst();
            if (newEnemyMonster.isPresent()) {
                _enemy.setActiveMonster(newEnemyMonster.get());
                _eventqueue.add(new MonsterSwitchBattleEvent(_enemy, newEnemyMonster.get()));
            } else {
                _eventqueue.add(new VictoryBattleEvent());
                return;
            }
        }

        //Enemy select random move
        doEnemyMove();
    }

    @Override
    public void switchMonster(IBattleParticipant battleParticipant, IMonster monster) {

        if (!battleParticipant.getMonsterTeam().contains(monster)) {
            throw new IllegalArgumentException(String.format("The battleparticipant does not have the monster %s in their team", monster.getName()));
        }

        battleParticipant.setActiveMonster(monster);
        _eventqueue.add(new MonsterSwitchBattleEvent(battleParticipant, monster));
    }

    @Override
    public void runAway(IBattleParticipant battleParticipant) {
        _eventqueue.add(new VictoryBattleEvent("You can for your life. You won anyway, lol"));
    }

    private void doEnemyMove() {
        _eventqueue.add(new MonsterAttackMoveBattleEvent(
                _enemy.getActiveMonster(),
                _player.getActiveMonster(),
                _enemy.getActiveMonster().getMoves().stream().findAny().orElse(_defaultMove),
                _enemy));
    }
}
