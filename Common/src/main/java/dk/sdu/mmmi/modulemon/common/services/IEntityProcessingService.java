package dk.sdu.mmmi.modulemon.common.services;

import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
