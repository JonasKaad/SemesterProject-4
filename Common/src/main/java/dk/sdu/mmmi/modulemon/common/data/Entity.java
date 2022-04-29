package dk.sdu.mmmi.modulemon.common.data;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.entityparts.EntityPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    private EntityType type;
    private float posX;
    private float posY;
    private Texture spriteTexture = null;
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

    public String getID() {
        return ID.toString();
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Texture getSpriteTexture() {
        return spriteTexture;
    }

    public void setSpriteTexture(Texture spriteTexture) {
        this.spriteTexture = spriteTexture;
    }

    public EntityType getType() {
        return type;
    }
}
