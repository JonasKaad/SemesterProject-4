package dk.sdu.mmmi.modulemon.Interaction;

import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;
import dk.sdu.mmmi.modulemon.common.data.entityparts.InteractPart;
import dk.sdu.mmmi.modulemon.common.services.IPostEntityProcessingService;

public class InteractProcessing implements IPostEntityProcessingService {
    private IMapView mapView;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            InteractPart interactPart = entity.getPart(InteractPart.class);
            if (interactPart != null) {
                if (interactPart.canInteract()) {
                    mapView.startEncounter(entity, interactPart.getInteractWith());
                }
            }
        }
    }

    public void setMapView(IMapView mapView) {
        this.mapView = mapView;
    }

    public void removeMapView(IMapView mapView) {
        this.mapView = null;
    }
}
