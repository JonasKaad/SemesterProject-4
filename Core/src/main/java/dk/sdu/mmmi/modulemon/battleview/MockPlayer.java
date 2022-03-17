package dk.sdu.mmmi.modulemon.battleview;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class MockPlayer implements IBattleParticipant {
    @Override
    public boolean isPlayerControlled() {
        return true;
    }

    @Override
    public IMonster getActiveMonster() {
        return new IMonster() {
            @Override
            public String getName() {
                return "Swag monster";
            }

            @Override
            public int getHitPoints() {
                return 100;
            }

            @Override
            public void setHitPoints(int hitPoint) {
                ;
            }

            @Override
            public int getAttack() {
                return 100;
            }

            @Override
            public int getDefence() {
                return 100;
            }

            @Override
            public int getSpeed() {
                return 100;
            }

            @Override
            public Texture getSprite() {
                return null;
            }

            @Override
            public MonsterType getMonsterType() {
                return MonsterType.FIRE;
            }

            @Override
            public List<IMonsterMove> getMoves() {
                List<IMonsterMove> moves = new ArrayList<>();
                moves.add(new MockMonsterMove("Swag move", MonsterType.FIRE, 10));
                moves.add( new MockMonsterMove("Ding dong", MonsterType.AIR, 1000));
                return moves;
            }
        };
    }

    @Override
    public void setActiveMonster(IMonster monster) {
        ;
    }

    @Override
    public List<IMonster> getMonsterTeam() {
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(getActiveMonster());
        return monsters;
    }
}