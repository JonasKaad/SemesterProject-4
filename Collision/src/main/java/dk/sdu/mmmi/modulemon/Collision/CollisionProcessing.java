package dk.sdu.mmmi.modulemon.Collision;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityType;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IPostEntityProcessingService;

public class CollisionProcessing implements IPostEntityProcessingService {
    private IMapView mapView;
    private AssetLoader loader = AssetLoader.getInstance();
    private float bonkCooldown;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            PositionPart entityPosPart = entity.getPart(PositionPart.class);
            if(mapView.isCellBlocked(entityPosPart.getTargetPos().x, entityPosPart.getTargetPos().y)) {
                entityPosPart.setTargetPos(entityPosPart.getX(), entityPosPart.getY());
                if (bonkCooldown <= 0 && entity.getType().equals(EntityType.PLAYER)) {
                    loader.getSoundAsset("/sounds/bonk.ogg", this.getClass()).play(gameData.getSoundVolume());
                    bonkCooldown = 0.5f;
                }
            }

            for (Entity checking : world.getEntities()) {
                if(entity.equals(checking))
                    continue;

                PositionPart checkPosPart = checking.getPart(PositionPart.class);

                if(entityPosPart.getTargetPos().equals(checkPosPart.getCurrentPos()) && !checkPosPart.getCurrentPos().equals(new Vector2(0,0))){
                    entityPosPart.setTargetPos(entityPosPart.getX(), entityPosPart.getY());
                }
                if(entityPosPart.getTargetPos().equals(checkPosPart.getTargetPos())){
                    float entityDistanceToTarget = entityPosPart.getTargetPos().dst(entityPosPart.getCurrentPos());
                    float checkingDistanceToTarget = checkPosPart.getTargetPos().dst(checkPosPart.getCurrentPos());
                    if(entityDistanceToTarget > checkingDistanceToTarget){
                        entityPosPart.setTargetPos(entityPosPart.getX(), entityPosPart.getY());
                    } else {
                        checkPosPart.setTargetPos(checkPosPart.getX(), checkPosPart.getY());
                    }
                }
            }
        }
        if(bonkCooldown >= 0){
            bonkCooldown -= gameData.getDelta();
        }
    }

    public void setMapView(IMapView mapView){
        this.mapView = mapView;
    }

    public void removeMapView(IMapView mapView){
        this.mapView = null;
    }
}
