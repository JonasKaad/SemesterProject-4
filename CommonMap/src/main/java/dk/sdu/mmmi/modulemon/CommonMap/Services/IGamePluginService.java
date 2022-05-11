package dk.sdu.mmmi.modulemon.CommonMap.Services;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);
    void stop(GameData gameData, World world);
}
