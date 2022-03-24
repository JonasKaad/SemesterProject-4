package dk.sdu.mmmi.modulemon.common.data;

import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

public interface IGameStateManager {
    void setDefaultState();
    void setState(IGameViewService state);
}
