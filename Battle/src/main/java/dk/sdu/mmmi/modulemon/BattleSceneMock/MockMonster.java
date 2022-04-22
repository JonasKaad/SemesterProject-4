package dk.sdu.mmmi.modulemon.BattleSceneMock;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class MockMonster implements IMonster {
    private String name;
    private int maxHitpoints;
    private int hitpoints;
    private int attackStat;
    private int defenceStat;
    private int speedStat;
    private String frontSprite;
    private String backSprite;
    private MonsterType type;
    private List<IMonsterMove> moves;

    public MockMonster(String name, int hitpoints, int attackStat, int defenceStat, int speedStat, String frontSprite, String backSprite, MonsterType type, List<IMonsterMove> moves) {
        this.name = name;
        this.maxHitpoints = hitpoints;
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
    public int getMaxHitPoints() {
        return this.maxHitpoints;
    }

    @Override
    public int getHitPoints() {
        return this.hitpoints;
    }

    @Override
    public void setHitPoints(int hitPoint) {
        this.hitpoints = hitPoint;
    }

    public int getAttack() {
        return this.attackStat;
    }

    public int getDefence() {
        return this.defenceStat;
    }

    public int getSpeed() {
        return this.speedStat;
    }

    @Override
    public String getFrontSprite() {
        return this.frontSprite;
    }

    @Override
    public String getBackSprite() {
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

    @Override
    public List<String> getStats() {
        List<String> statList = new ArrayList<>();
        statList.add("Defence: " + this.getDefence());
        statList.add("Attack: " + this.getAttack());
        statList.add("Speed: " + this.getSpeed());
        return statList;
    }

    @Override
    public IMonster clone() {
        return null;
    }
  
    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
