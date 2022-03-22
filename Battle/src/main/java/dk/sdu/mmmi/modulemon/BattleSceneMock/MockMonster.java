package dk.sdu.mmmi.modulemon.BattleSceneMock;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.List;

public class MockMonster implements IMonster {
    private String name;
    private int hitpoints;
    private int attackStat;
    private int defenceStat;
    private int speedStat;
    private Texture frontSprite;
    private Texture backSprite;
    private MonsterType type;
    private List<IMonsterMove> moves;

    public MockMonster(String name, int hitpoints, int attackStat, int defenceStat, int speedStat, Texture frontSprite, Texture backSprite, MonsterType type, List<IMonsterMove> moves) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.attackStat = attackStat;
        this.defenceStat = defenceStat;
        this.speedStat = speedStat;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
        this.type = type;
        this.moves = moves;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHitPoints() {
        return this.hitpoints;
    }

    @Override
    public void setHitPoints(int hitPoint) {
        this.hitpoints = hitPoint;
    }

    @Override
    public int getAttack() {
        return this.attackStat;
    }

    @Override
    public int getDefence() {
        return this.defenceStat;
    }

    @Override
    public int getSpeed() {
        return this.speedStat;
    }

    @Override
    public Texture getFrontSprite() {
        return this.frontSprite;
    }

    @Override
    public Texture getBackSprite() {
        return this.backSprite;
    }

    @Override
    public MonsterType getMonsterType() {
        return this.type;
    }

    @Override
    public List<IMonsterMove> getMoves() {
        return this.moves;
    }
}
