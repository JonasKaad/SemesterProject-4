package dk.sdu.mmmi.modulemon.Battle;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BattleParticipantTest {
    @Test
    void clone_returns_new_object() {
        IMonster monster1 = mock(IMonster.class);
        IMonster monster2 = mock(IMonster.class);
        List<IMonster> team = new ArrayList<>();
        team.add(monster1);
        team.add(monster2);
        boolean ipc = true;
        BattleParticipant participant = new BattleParticipant(team, monster1, ipc);
        BattleParticipant clone = (BattleParticipant) participant.clone();
        assertNotEquals(participant, clone);
    }

    @Test
    void clone_should_have_same_attributes() {
        IMonster monster1 = spy(mock(IMonster.class));
        IMonster monster2 = spy(mock(IMonster.class));
        List<IMonster> team = new ArrayList<>();
        team.add(monster1);
        team.add(monster2);
        boolean ipc = true;
        BattleParticipant participant = new BattleParticipant(team, monster1, ipc);
        BattleParticipant clone = (BattleParticipant) participant.clone();
        assertNotEquals(participant.getActiveMonster(), clone.getActiveMonster());
        assertEquals(ipc, clone.isPlayerControlled());
        verify(monster1).clone();
        verify(monster2).clone();
    }
}
