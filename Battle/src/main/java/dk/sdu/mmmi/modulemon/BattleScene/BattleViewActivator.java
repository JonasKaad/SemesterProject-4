package dk.sdu.mmmi.modulemon.BattleScene;

import dk.sdu.mmmi.modulemon.CommonBattleClient.IBattleView;
import dk.sdu.mmmi.modulemon.common.services.IGameViewService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BattleViewActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        /*
        BattleView bv = new BattleView();
        bundleContext.registerService(IBattleView.class, bv, null);
        bundleContext.registerService(IGameViewService.class, bv, null);

         */
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
