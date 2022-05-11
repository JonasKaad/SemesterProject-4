package dk.sdu.mmmi.modulemon.BundleController;

import org.apache.felix.scr.Component;

import java.util.Comparator;

public class ComponentComparer implements Comparator<Component> {
    @Override
    public int compare(Component o1, Component o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
