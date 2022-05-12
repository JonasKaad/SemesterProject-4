package dk.sdu.mmmi.modulemon.CommonMap.Data;

import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.EntityPart;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    private EntityType type;
    private Map<Class, EntityPart> parts;

    public Entity(EntityType type) {
        this.type = type;
        parts = new ConcurrentHashMap<>();
    }

    public Entity(){
        this(EntityType.GENERIC);
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public Collection<EntityPart> getParts() {
        return parts.values();
    }

    public String getID() {
        return ID.toString();
    }

    public EntityType getType() {
        return type;
    }
}
