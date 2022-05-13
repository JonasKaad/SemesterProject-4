package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.CommonMap.IMapEvent;
import dk.sdu.mmmi.modulemon.common.data.GameData;

public class InteracteePart implements EntityPart{
    private InteracteePartDelegate delegate;
    public InteracteePart(InteracteePartDelegate delegate){
        if(delegate == null){
            throw new IllegalArgumentException("Delegate is null");
        }
        this.delegate = delegate;
    }

    public IMapEvent getEvent(Entity intactor){
        return delegate.getEvent(intactor);
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }

    public interface InteracteePartDelegate{
        IMapEvent getEvent(Entity inteactor);
    }
}
