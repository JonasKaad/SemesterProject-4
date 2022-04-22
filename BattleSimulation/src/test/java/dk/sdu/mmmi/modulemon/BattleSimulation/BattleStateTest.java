package dk.sdu.mmmi.modulemon.BattleSimulation;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleStateTest {
    @Test
    void clone_creates_new_object_and_clones_participants() {
        IBattleParticipant player = spy(mock(IBattleParticipant.class));
        IBattleParticipant enemy = spy(mock(IBattleParticipant.class));
        BattleState battleState = new BattleState(player, enemy);
        battleState.setActiveParticipant(player);
        BattleState clone = (BattleState) battleState.clone();
        assertNotEquals(battleState, clone);
        verify(player).clone();
        verify(enemy).clone();
    }
}
