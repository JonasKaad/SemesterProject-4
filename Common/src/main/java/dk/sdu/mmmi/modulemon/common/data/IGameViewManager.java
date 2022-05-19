package dk.sdu.mmmi.modulemon.common.data;

import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

public interface IGameViewManager {
    void setDefaultState();

    void setState(IGameViewService state, boolean disposeCurrent);

    void setState(IGameViewService state);
}
