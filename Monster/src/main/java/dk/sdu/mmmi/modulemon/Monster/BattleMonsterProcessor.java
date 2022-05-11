package dk.sdu.mmmi.modulemon.Monster;

import dk.sdu.mmmi.modulemon.CommonBattleSimulation.IBattleMonsterProcessor;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonsterMove;

public class BattleMonsterProcessor implements IBattleMonsterProcessor {

    @Override
    public IMonster whichMonsterStarts(IMonster iMonster1, IMonster iMonster2) {
        Monster monster1 = (Monster) iMonster1;
        Monster monster2 = (Monster) iMonster2;
        return monster1.getSpeed() >= monster2.getSpeed() ? monster1 : monster2;
    }

    @Override
    public int calculateDamage(IMonster iSource, IMonsterMove iMove, IMonster iTarget) {
        Monster source = (Monster) iSource;
        Monster target = (Monster) iTarget;
        MonsterMove move = (MonsterMove) iMove;
        float moveDamage = (float) move.getDamage();
        float sourceAttack = (float) source.getAttack();
        float targetDefence = (float) target.getDefence();



        // Same type attack bonus. Effectively the same as STAB in that other game
        boolean same_attack_type = source.getMonsterType() == move.getType();
        float attack_bonus = 1;

        if(same_attack_type){
            attack_bonus = 1.5f;
        }


        int damage =  Math.round( ( (0.2f * sourceAttack + 3 + 20 ) / (targetDefence + 50) ) * moveDamage * attack_bonus * calculateTypeAdvantage(move.getType(), target.getMonsterType()) );
        return damage;
    }

    public float calculateTypeAdvantage(MonsterType source, MonsterType target) {
        switch (source) {
            case NORMAL:
                switch (target) {
                    default: return 1;
                }
            case FIRE:
                switch (target) {
                    case FIRE: return 0.5f;
                    case WATER: return 0.5f;
                    case GRASS: return 2;
                    default: return 1;
                }
            case AIR:
                switch (target) {
                    case GRASS: return 2;
                    case LIGHTNING: return 0.5f;
                    default: return 1;
                }
            case EARTH:
                switch (target) {
                    case AIR: return 0;
                    case FIRE: return 2;
                    case GRASS: return 0.5f;
                    case LIGHTNING: return 2;
                    default: return 1;
                }
            case GRASS:
                switch (target) {
                    case AIR: return 0.5f;
                    case EARTH: return 2;
                    case FIRE: return 0.5f;
                    case GRASS: return 0.5f;
                    default: return 1;
                }
            case WATER:
                switch (target) {
                    case EARTH: return 2;
                    case FIRE: return 2;
                    case WATER: return 0.5f;
                    case GRASS: return 0.5f;
                    default: return 1;
                }
            case LIGHTNING:
                switch (target) {
                    case AIR: return 2;
                    case EARTH: return 0;
                    case WATER: return 2;
                    case GRASS: return 0.5f;
                    case LIGHTNING: return 0.5f;
                    default: return 1;
                }
            default: return 1;
        }
    }

}
