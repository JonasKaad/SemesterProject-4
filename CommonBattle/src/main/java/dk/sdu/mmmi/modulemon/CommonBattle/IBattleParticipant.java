package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;

import java.util.List;

public interface IBattleParticipant {
    boolean isPlayerControlled();
    IMonster getActiveMonster();
    void setActiveMonster(IMonster monster);
    List<IMonster> getMonsterTeam();
    IBattleParticipant clone();
}
