package dk.sdu.mmmi.modulemon.BattleSceneMock;

import dk.sdu.mmmi.modulemon.Battle.BattleParticipant;
import dk.sdu.mmmi.modulemon.CommonBattle.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterRegistry;
import dk.sdu.mmmi.modulemon.Monster.MonsterRegistry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * A static helper class that just instansiates some Battle participants with some monsters, while we don't dynimically create those
 */
public class BattleParticipantMocks {

    public static IBattleParticipant getPlayer() throws IOException, URISyntaxException {
        IMonsterRegistry monsterRegistry = new MonsterRegistry();
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(monsterRegistry.getMonster(0));
        monsters.add(monsterRegistry.getMonster(1));

        IBattleParticipant player = new BattleParticipant(monsters, true);
        return player;
    }

    public static IBattleParticipant getOpponent() throws IOException, URISyntaxException {
        IMonsterRegistry monsterRegistry = new MonsterRegistry();
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(monsterRegistry.getMonster(2));
        monsters.add(monsterRegistry.getMonster(3));

        IBattleParticipant enemy = new BattleParticipant(monsters, false);
        return enemy;
    }

}
