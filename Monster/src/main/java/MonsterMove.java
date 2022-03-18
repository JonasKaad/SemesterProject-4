import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;
import dk.sdu.mmmi.modulemon.CommonMonster.MonsterType;

public class MonsterMove implements IMonsterMove {

    private String name;
    private int damage;
    private MonsterType type;

    public MonsterMove(String name, int damage, MonsterType type) {
        this.name = name;
        this.damage = damage;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public MonsterType getType() {
        return type;
    }
}
