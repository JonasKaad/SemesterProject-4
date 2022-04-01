package dk.sdu.mmmi.modulemon.CommonMap;

public interface IMapView {
    float getMapLeft();
    float getMapRight();
    float getMapBottom();
    float getMapTop();
    int getTileSize();
    boolean isCellBlocked(float x, float y);
    boolean isPaused();
}

