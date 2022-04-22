package dk.sdu.mmmi.modulemon.BattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.BattleEvents.*;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleAI;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleMonsterProcessor;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleSimulation;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleSimulationTest {

    IBattleParticipant player;
    IBattleParticipant enemy;
    IMonster playerMonster;
    IMonster enemyMonster;
    List<IMonsterMove> moveList;

    @BeforeEach
    void beforeEach() {
        player = null;
        enemy = null;
        playerMonster = null;
        enemyMonster = null;
    }

    private void initMonsterMocks() {

        playerMonster = mock(IMonster.class);
        enemyMonster = mock(IMonster.class);

        IMonsterMove simpleMove = mock(IMonsterMove.class);
        when(simpleMove.getName()).thenReturn("basic attack");
        when(simpleMove.getDamage()).thenReturn(10);
        moveList = new ArrayList<>();
        moveList.add(simpleMove);

        when(playerMonster.getMoves()).thenReturn(moveList);
        when(enemyMonster.getMoves()).thenReturn(moveList);
        when(playerMonster.getHitPoints()).thenReturn(50);
        when(enemyMonster.getHitPoints()).thenReturn(50);

    }

    private void initBattleParticipantMocks() {

        player = mock(IBattleParticipant.class);
        enemy = mock(IBattleParticipant.class);
        when(player.isPlayerControlled()).thenReturn(true);
        when(enemy.isPlayerControlled()).thenReturn(false);
        when(player.getActiveMonster()).thenReturn(playerMonster);
        when(enemy.getActiveMonster()).thenReturn(enemyMonster);
    }

    private BattleSimulation startBattle() {
        BattleSimulation sim = new BattleSimulation();
        // Mock battleProcessor
        sim.setMonsterProcessor(new IBattleMonsterProcessor() {
            @Override
            public IMonster whichMonsterStarts(IMonster monster1, IMonster monster2) {
                return monster2;
            }

            @Override
            public int calculateDamage(IMonster source, IMonsterMove move, IMonster target) {
                return move.getDamage();
            }
        });

        sim.setAI(new IBattleAI() {
            @Override
            public void doAction(IBattleSimulation battleSimulation) {
                IBattleParticipant activeParticipant =
                        battleSimulation.getState().isPlayersTurn()
                                ? battleSimulation.getState().getPlayer()
                                : battleSimulation.getState().getEnemy();
                battleSimulation.doMove(activeParticipant, activeParticipant.getActiveMonster().getMoves().get(0));
            }
        });

        sim.StartBattle(player, enemy);
        return sim;
    }

    @Test
    void shouldContainPlayer() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        assertEquals(player, battleSimulation.getState().getPlayer());
    }

    @Test
    void shouldContainEnemy() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        assertEquals(enemy, battleSimulation.getState().getEnemy());
    }

    @Test
    void firstTwoEventsShouldBeInfoAndMove() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertEquals("enemy starts the battle", event.getText());
        event = battleSimulation.getNextBattleEvent();
        assertEquals("enemy monster used basic attack", event.getText().substring(0, 31));
    }

    @Test
    void shouldNotBePlayersTurnBeforeFirstEvent() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        assertFalse(battleSimulation.getState().isPlayersTurn());
    }

    @Test
    void playerMoveShouldResultInMoveEvent() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        battleSimulation.doMove(player, player.getActiveMonster().getMoves().get(0));
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(MoveBattleEvent.class, event);
        assertEquals("player monster used basic attack", event.getText().substring(0, 32));
    }

    @Test
    void shouldThrowErrorWhenUsingIllegalMove() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        assertThrows(IllegalArgumentException.class, () ->
                battleSimulation.doMove(player, mock(IMonsterMove.class))
        );
    }

    @Test
    void playerMonsterShouldTakeDamage() {
        initMonsterMocks();
        playerMonster = spy(playerMonster);
        when(playerMonster.getHitPoints()).thenReturn(50);
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        verify(playerMonster).setHitPoints(40);

    }

    @Test
    void enemyMonsterShouldTakeDamage() {
        initMonsterMocks();
        enemyMonster = spy(enemyMonster);
        when(enemyMonster.getMoves()).thenReturn(moveList);
        when(enemyMonster.getHitPoints()).thenReturn(50);
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        battleSimulation.doMove(player, player.getActiveMonster().getMoves().get(0));
        battleSimulation.getNextBattleEvent();
        verify(enemyMonster).setHitPoints(40);
    }

    @Test
    void shouldNotBeAbleToDoMoveWhenNotTheirTurn() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    battleSimulation.doMove(player, playerMonster.getMoves().get(0));
                });
    }

    @Test
    void shouldNotBeAbleToDoSwitchMonsterWhenNotTheirTurn() {
        initMonsterMocks();
        IMonster newMonster = mock(IMonster.class);
        List<IMonster> playerTeam = new ArrayList<>();
        playerTeam.add(playerMonster);
        playerTeam.add(newMonster);
        initBattleParticipantMocks();
        player = spy(player);
        when(player.isPlayerControlled()).thenReturn(true);
        when(player.getActiveMonster()).thenReturn(playerMonster);
        when(player.getMonsterTeam()).thenReturn(playerTeam);
        BattleSimulation battleSimulation = startBattle();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    battleSimulation.switchMonster(player, newMonster);
                });
    }

    @Test
    void shouldNotBeAbleToRunAwayWhenNotTheirTurn() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        assertThrows(IllegalArgumentException.class,
                () -> {
                    battleSimulation.runAway(player);
                });
    }

    @Test
    void playerCanChangeMonster() {
        initMonsterMocks();
        IMonster newMonster = mock(IMonster.class);
        List<IMonster> playerTeam = new ArrayList<>();
        playerTeam.add(playerMonster);
        playerTeam.add(newMonster);
        initBattleParticipantMocks();
        player = spy(player);
        when(player.isPlayerControlled()).thenReturn(true);
        when(player.getActiveMonster()).thenReturn(playerMonster);
        when(player.getMonsterTeam()).thenReturn(playerTeam);
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        assertEquals(playerMonster, battleSimulation.getState().getPlayer().getActiveMonster());
        battleSimulation.switchMonster(player, newMonster);
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(ChangeMonsterBattleEvent.class, event);
        event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(MoveBattleEvent.class, event, "It should be the enemy's turn, when the player has changed their monster");
        verify(player).setActiveMonster(newMonster);
    }

    @Test
    void playerCanRunAway() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        battleSimulation.runAway(player);
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(RunAwayBattleEvent.class, event);
        assertEquals("player", event.getText().substring(0, 6));
    }

    @Test
    void ParticipantsCanTakeSeveralTurns() {
        initMonsterMocks();
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        for (int i = 0; i<5; i++) {
            while (!battleSimulation.getState().isPlayersTurn()) {
                battleSimulation.getNextBattleEvent();
            }
            battleSimulation.doMove(player, playerMonster.getMoves().get(0));
            assertInstanceOf(MoveBattleEvent.class, battleSimulation.getNextBattleEvent());
        }
    }

    @Test
    void playerShouldWin() {
        initMonsterMocks();
        when(enemyMonster.getHitPoints()).thenReturn(10);
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();
        while (!battleSimulation.getState().isPlayersTurn()) {
            battleSimulation.getNextBattleEvent();
        }
        battleSimulation.doMove(player, player.getActiveMonster().getMoves().get(0));
        battleSimulation.getNextBattleEvent();
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(VictoryBattleEvent.class, event);
        VictoryBattleEvent vEvent = (VictoryBattleEvent) event;
        assertEquals(player, vEvent.getWinner());
    }

    @Test
    void enemyShouldWin() {
        initMonsterMocks();
        when(playerMonster.getHitPoints()).thenReturn(10);
        initBattleParticipantMocks();
        BattleSimulation battleSimulation = startBattle();

        //BattleStartEvent
        assertInstanceOf(InfoBattleEvent.class, battleSimulation.getNextBattleEvent());
        //EnemyMoveEvent
        assertInstanceOf(MoveBattleEvent.class, battleSimulation.getNextBattleEvent());
        //VictoryEvent
        IBattleEvent event = battleSimulation.getNextBattleEvent();
        assertInstanceOf(VictoryBattleEvent.class, event);
        VictoryBattleEvent vEvent = (VictoryBattleEvent) event;
        assertEquals(enemy, vEvent.getWinner());
    }

    @Test
    void ChangesMonsterOnFaint() {
        initMonsterMocks();
        when(playerMonster.getHitPoints()).thenReturn(10);
        initBattleParticipantMocks();
        player = spy(player);


        List<IMonster> playerTeam = new ArrayList<>();
        IMonster playerMonster2 = mock(IMonster.class);
        playerTeam.add(playerMonster2);
        playerTeam.add(playerMonster);
        when(playerMonster2.getHitPoints()).thenReturn(10);
        when(player.getMonsterTeam()).thenReturn(playerTeam);
        when(player.getActiveMonster()).thenReturn(playerMonster);
        when(player.isPlayerControlled()).thenReturn(true);
        when(enemy.isPlayerControlled()).thenReturn(false);
        BattleSimulation battleSimulation = startBattle();

        //BattleStartEvent
        assertInstanceOf(InfoBattleEvent.class, battleSimulation.getNextBattleEvent());
        //EnemyMoveEvent
        assertInstanceOf(MoveBattleEvent.class, battleSimulation.getNextBattleEvent());
        //ChangeMonsterEvent
        assertInstanceOf(ChangeMonsterBattleEvent.class, battleSimulation.getNextBattleEvent());
        battleSimulation.doMove(player, playerMonster.getMoves().get(0));
        //Player should do a move
        assertInstanceOf(MoveBattleEvent.class, battleSimulation.getNextBattleEvent());

        verify(player).setActiveMonster(playerMonster2);
    }

    @Test
    void simulateDoMoveWorks() {
        //TODO: Come up with a good way of testing this
    }

    @Test
    void simulateSwitchMonsterWorks() {
        //TODO: Come up with a good way of testing this
    }
}
