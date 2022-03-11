package dk.sdu.mmmi.modulemon.CommonBattleParticipant;

public interface IBattleParticipant {
    BattleParticipantType getParticipantType();

    IMonster getActiveMonster();

    void setActiveMonster(IMonster monster);

    List<IMonster> getMonsterTeam();
}
