package dk.sdu.mmmi.modulemon.CommonBattleParticipant;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

import java.util.List;

public interface IBattleParticipant {
    BattleParticipantType getParticipantType();

    IMonster getActiveMonster();

    void setActiveMonster(IMonster monster);

    List<IMonster> getMonsterTeam();
}
