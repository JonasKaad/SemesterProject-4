package dk.sdu.mmmi.modulemon.MapEntities;

import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.EntityPart;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VendingMachineVisitedPart implements EntityPart
{
    private List<UUID> uuids;
    public VendingMachineVisitedPart(){
        this.uuids = new ArrayList<>();
    }

    public void addUuid(UUID uuid){
        this.uuids.add(uuid);
    }

    public List<UUID> getUuids(){
        return this.uuids;
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}
