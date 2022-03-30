package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonBattle.IBattleMonsterProcessor;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class MonsterBattleProcessor implements IBattleMonsterProcessor {

    @Override
    public IMonster whichMonsterStarts(IMonster monster1, IMonster monster2) {
        return monster1.getSpeed() >= monster2.getSpeed() ? monster1 : monster2;
    }

    public int calculateDamage(IMonster source, IMonsterMove move, IMonster target) {
        float moveDamage = (float) move.getDamage();
        float sourceAttack = (float) source.getAttack();
        float targetDefence = (float) target.getDefence();

        int damage = Math.round(moveDamage*(sourceAttack/targetDefence));

        return damage;
    }

}
