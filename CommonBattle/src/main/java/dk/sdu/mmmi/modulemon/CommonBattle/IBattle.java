package dk.sdu.mmmi.modulemon.CommonBattle;

import dk.sdu.mmmi.modulemon.CommonBattleParticipant.IBattleParticipant;

public interface IBattle {
    void init(IBattleParticipant Player, IBattleParticipant Enemy);
    void update(float dt);
    void draw();
    void handleInput();
    void dispose();
}
