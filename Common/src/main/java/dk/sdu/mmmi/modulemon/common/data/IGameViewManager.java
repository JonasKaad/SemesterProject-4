package dk.sdu.mmmi.modulemon.common.data;

import dk.sdu.mmmi.modulemon.common.services.IGameViewService;

public interface IGameViewManager {
    void setDefaultView();

    void setView(IGameViewService view, boolean disposeCurrent);

    void setView(IGameViewService view);
}
