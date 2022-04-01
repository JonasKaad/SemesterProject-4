package dk.sdu.mmmi.modulemon.common.data.entityparts;


import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

public class SpritePart implements EntityPart{


    private String upSprite;
    private String downSprite;
    private String leftSprite;
    private String rightSprite;

    public SpritePart(String upSprite, String downSprite, String leftSprite, String rightSprite) {
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.leftSprite = leftSprite;
        this.rightSprite = rightSprite;
    }

    public String getUpSprite() {
        return upSprite;
    }

    public void setUpSprite(String upSprite) {
        this.upSprite = upSprite;
    }

    public String getDownSprite() {
        return downSprite;
    }

    public void setDownSprite(String downSprite) {
        this.downSprite = downSprite;
    }

    public String getLeftSprite() {
        return leftSprite;
    }

    public void setLeftSprite(String leftSprite) {
        this.leftSprite = leftSprite;
    }

    public String getRightSprite() {
        return rightSprite;
    }

    public void setRightSprite(String rightSprite) {
        this.rightSprite = rightSprite;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
