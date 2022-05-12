package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;


import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.data.GameData;

public class SpritePart implements EntityPart {
    private Texture upSprite;
    private Texture downSprite;
    private Texture leftSprite;
    private Texture rightSprite;

    private Texture currentSprite;

    public SpritePart(Texture upSprite, Texture downSprite, Texture leftSprite, Texture rightSprite) {
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
    }


    public Texture getUpSprite() {
        return upSprite;
    }

    public void setUpSprite(Texture upSprite) {
        this.upSprite = upSprite;
    }

    public Texture getDownSprite() {
        return downSprite;
    }

    public void setDownSprite(Texture downSprite) {
        this.downSprite = downSprite;
    }

    public Texture getLeftSprite() {
        return leftSprite;
    }

    public void setLeftSprite(Texture leftSprite) {
        this.leftSprite = leftSprite;
    }

    public Texture getRightSprite() {
        return rightSprite;
    }

    public void setRightSprite(Texture rightSprite) {
        this.rightSprite = rightSprite;
    }

    public Texture getCurrentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(Texture currentSprite) {
        this.currentSprite = currentSprite;
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}
