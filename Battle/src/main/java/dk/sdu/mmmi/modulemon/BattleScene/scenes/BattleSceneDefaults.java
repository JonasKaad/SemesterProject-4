package dk.sdu.mmmi.modulemon.BattleScene.scenes;

import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.modulemon.common.drawing.Position;

public class BattleSceneDefaults {

    //Welcome to the world of magical values!
    public static Position backdropPosition(){return new Position(0, 0);}
    public static Position playerBasePosition(){ return new Position(145, -8);}
    public static Position enemyBasePosition(){ return new Position(800, 400);}
    public static Position enemyMonsterPosition(){return new Position(850, 450);}
    public static Position playerMonsterPosition () {return new Position(320, 100);}
    public static Position enemyHealthBoxPosition(){ return new Position(480, 550);}
    public static float enemyHealthBoxWidth(){return 300f;}
    public static float enemyHealthBoxHeight(){return 100f;}
    public static Position playerHealthBoxPosition(){ return new Position(100, 300);}
    public static float playerHealthBoxWidth(){return 350f;}
    public static float playerHealthBoxHeight(){return 100f;}
    public static Position actionBoxPosition(float gameWidth){return new Position(gameWidth - 300, 155); }
    public static float actionBoxWidth(){return 250f;}
    public static float actionBoxHeight(){return 200f;}
    public static Position healthIndicatorPosition() { return new Position(-100, -100); };
    public static Position textBoxPosition(){ return new Position(20,20); };
    public static float textBoxHeight(){return 100f;}
    public static Color healthIndicatorColor() {return Color.RED;};
    public static float enemyMonsterRotation() { return 0f;}
    public static float playerMonsterRotation(){ return 0f;}

}
