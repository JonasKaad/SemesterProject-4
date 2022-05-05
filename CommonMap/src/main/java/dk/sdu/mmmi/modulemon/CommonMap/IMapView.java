package dk.sdu.mmmi.modulemon.CommonMap;

import dk.sdu.mmmi.modulemon.common.data.Entity;

public interface IMapView {
    float getMapLeft();
    float getMapRight();
    float getMapBottom();
    float getMapTop();
    int getTileSize();
    boolean isCellBlocked(float x, float y);
    boolean isPaused();
    void startEncounter(Entity player, Entity enemy);
}

