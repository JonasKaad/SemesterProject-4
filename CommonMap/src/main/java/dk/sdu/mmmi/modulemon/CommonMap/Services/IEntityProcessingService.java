package dk.sdu.mmmi.modulemon.CommonMap.Services;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
