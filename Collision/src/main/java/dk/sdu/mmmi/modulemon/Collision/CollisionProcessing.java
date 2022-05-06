package dk.sdu.mmmi.modulemon.Collision;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.modulemon.common.services.IPostEntityProcessingService;

public class CollisionProcessing implements IPostEntityProcessingService {
    private IMapView mapView;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            PositionPart entityPosPart = entity.getPart(PositionPart.class);
            if(mapView.isCellBlocked(entityPosPart.getTargetPos().x, entityPosPart.getTargetPos().y)){
                entityPosPart.setTargetPos(entityPosPart.getX(), entityPosPart.getY());
            }
            /*
            for (Entity checking : world.getEntities()) {
                if(entity.equals(checking))
                    continue;

                PositionPart checkPosPart = checking.getPart(PositionPart.class);

                if(entityPosPart.getTargetPos().equals(checkPosPart.getCurrentPos()) && !checkPosPart.getCurrentPos().equals(new Vector2(0,0))){
                    entityPosPart.setTargetPos(entityPosPart.getCurrentPos());
                }
                if(entityPosPart.getTargetPos().equals(checkPosPart.getTargetPos())){
                    float entityDistanceToTarget = entityPosPart.getTargetPos().dst(entityPosPart.getCurrentPos());
                    float checkingDistanceToTarget = checkPosPart.getTargetPos().dst(checkPosPart.getCurrentPos());
                    if(entityDistanceToTarget > checkingDistanceToTarget){
                        entityPosPart.setTargetPos(entityPosPart.getCurrentPos());
                    } else {
                        checkPosPart.setTargetPos(checkPosPart.getCurrentPos());
                    }
                }
            }
             */
        }
    }

    public void setMapView(IMapView mapView){
        this.mapView = mapView;
    }

    public void removeMapView(IMapView mapView){
        this.mapView = null;
    }
}
