package dk.sdu.mmmi.modulemon.Collision;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityType;
import dk.sdu.mmmi.modulemon.common.SettingsRegistry;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.PositionPart;
import dk.sdu.mmmi.modulemon.CommonMap.Services.IPostEntityProcessingService;
import dk.sdu.mmmi.modulemon.common.services.IGameSettings;

public class CollisionProcessing implements IPostEntityProcessingService {
    private IMapView mapView;
    private AssetLoader loader = AssetLoader.getInstance();
    private float bonkCooldown;
    private IGameSettings settings;

    @Override
    public void process(GameData gameData, World world) {
        if(mapView == null){
            return;
        }
        for (Entity entity : world.getEntities()) {
            PositionPart entityPosPart = entity.getPart(PositionPart.class);
            if(mapView.isCellBlocked(entityPosPart.getTargetPos().x, entityPosPart.getTargetPos().y) && !entityPosPart.getTargetPos().equals(new Vector2(0,0))) {
                entityPosPart.setTargetPos(entityPosPart.getX(), entityPosPart.getY());
                if (bonkCooldown <= 0 && entity.getType().equals(EntityType.PLAYER)) {
                    if(settings != null){
                        loader.getSoundAsset("/sounds/bonk.ogg", this.getClass()).play( ((int) settings.getSetting(SettingsRegistry.getInstance().getSoundVolumeSetting()) / 100f) / 2f);
                    }
                    else loader.getSoundAsset("/sounds/bonk.ogg", this.getClass()).play( );
                    bonkCooldown = 0.5f;
                }
                return;
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
    
    public void setSettingsService(IGameSettings settings){
        this.settings = settings;
        if (settings.getSetting(SettingsRegistry.getInstance().getSoundVolumeSetting())==null) {
            settings.setSetting(SettingsRegistry.getInstance().getSoundVolumeSetting(), 60);
        }

    }

    public void removeSettingsService(IGameSettings settings){
        this.settings = null;
    }
}
