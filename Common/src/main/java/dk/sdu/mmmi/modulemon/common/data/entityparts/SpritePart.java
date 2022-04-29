package dk.sdu.mmmi.modulemon.common.data.entityparts;


import com.badlogic.gdx.graphics.Texture;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;

public class SpritePart implements EntityPart{
    private Texture upSprite;
    private Texture downSprite;
    private Texture leftSprite;
    private Texture rightSprite;

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

    @Override
    public void process(GameData gameData, World world, Entity entity) {

    }
}
