package dk.sdu.mmmi.modulemon.BattleSceneMock;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class MockEnemy implements IBattleParticipant {
    private IMonster monster;

    public MockEnemy(){
        monster = new IMonster() {
            private int health = 100;
            @Override
            public String getName() {
                return "Wooper";
            }

            @Override
            public int getHitPoints() {
                return health;
            }

            @Override
            public void setHitPoints(int hitPoint) {
                this.health = hitPoint;
            }

            @Override
            public int getAttack() {
                return 10;
            }

            @Override
            public int getDefence() {
                return 3;
            }

            @Override
            public int getSpeed() {
                return 50;
            }

            @Override
            public Texture getSprite() {
                return null;
            }

            @Override
            public MonsterType getMonsterType() {
                return MonsterType.GRASS;
            }

            @Override
            public List<IMonsterMove> getMoves() {
                List<IMonsterMove> moves = new ArrayList<>();
                moves.add(new MockMonsterMove("Crap move", MonsterType.GRASS, 1));
                return moves;
            }
        };
    }

    @Override
    public boolean isPlayerControlled() {
        return false;
    }

    @Override
    public IMonster getActiveMonster() {
        return monster;
    }

    @Override
    public void setActiveMonster(IMonster monster) {
        this.monster = monster;
    }

    @Override
    public List<IMonster> getMonsterTeam() {
        List<IMonster> monsters = new ArrayList<>();
        monsters.add(monster);
        return monsters;
    }
}
