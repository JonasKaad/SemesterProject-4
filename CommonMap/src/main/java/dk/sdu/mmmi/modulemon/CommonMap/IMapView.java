package dk.sdu.mmmi.modulemon.CommonMap;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;

public interface IMapView {
    float getMapLeft();
    float getMapRight();
    float getMapBottom();
    float getMapTop();
    int getTileSize();
    boolean isCellBlocked(float x, float y);
    void startEncounter(Entity player, Entity enemy);
    void addMapEvent(IMapEvent event);
}

