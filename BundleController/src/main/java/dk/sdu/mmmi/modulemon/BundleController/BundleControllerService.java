package dk.sdu.mmmi.modulemon.BundleController;

import dk.sdu.mmmi.modulemon.common.services.IBundleControllerService;

public class BundleControllerService implements IBundleControllerService {
    private boolean isOpen = false;
    private BundleControllerUI bundleControllerUI;

    @Override
    public void openController() {
        if(!isOpen) {
            isOpen = true;
            bundleControllerUI = new BundleControllerUI(this);
            bundleControllerUI.setVisible(true);
            bundleControllerUI.setAlwaysOnTop(true);
            return;
        }

        // Is already open
        bundleControllerUI.requestFocus();
    }

    protected void setClosed(){
        bundleControllerUI = null;
        isOpen = false;
    }
}
