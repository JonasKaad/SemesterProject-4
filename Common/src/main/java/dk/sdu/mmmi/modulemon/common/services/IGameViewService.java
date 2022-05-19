package dk.sdu.mmmi.modulemon.common.services;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.IGameViewManager;

public interface IGameViewService {
    void init(IGameViewManager gameViewManager);

    void update(GameData gameData, IGameViewManager gameViewManager);

    void draw(GameData gameData);

    void handleInput(GameData gameData, IGameViewManager gameViewManager);

    void dispose();
}
