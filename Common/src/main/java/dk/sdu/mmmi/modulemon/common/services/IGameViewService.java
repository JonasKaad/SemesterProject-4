package dk.sdu.mmmi.modulemon.common.services;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameStateManager;
import dk.sdu.mmmi.modulemon.common.data.World;

public interface IGameViewService {
    void init(IGameStateManager gameStateManager);

    void update(GameData gameData, IGameStateManager gameStateManager);

    void draw(GameData gameData);

    void handleInput(GameData gameData, IGameStateManager gameStateManager);

    void dispose();
}
