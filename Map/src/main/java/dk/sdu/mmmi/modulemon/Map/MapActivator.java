package dk.sdu.mmmi.modulemon.Map;

import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class MapActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        /*
        MapView mapView = new MapView();
        bundleContext.registerService(IMapView.class, mapView, null);
        bundleContext.registerService(IGameViewService.class, mapView, null);

         */
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
