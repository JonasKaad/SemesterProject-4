/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

import static java.lang.Math.*;

public class MovingPart extends BaseAnimation implements EntityPart {

    private boolean left, right, up, down;
    private float movingTimer = 0;
    private float animationTimer;
    Vector2 vector2 = new Vector2();


    public MovingPart() {
        super();
        Timeline = new int[]{0, 2000, 2500};
        States = new ArrayList<>(Timeline.length);
    }
    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public Vector2 lerpT(Vector2 currentPos, Vector2 newPos, float alpha)
    {
        //return currentPos.scl(1f - alpha).add(newPos.scl(alpha));
        float x = currentPos.x + alpha * (newPos.x - currentPos.x);
        float y = currentPos.y + alpha * (newPos.y - currentPos.y);

        return new Vector2(x, y);
    }

    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float start_x = positionPart.getX();
        float y = positionPart.getY();
        float start_y = positionPart.getY();
        float dt = gameData.getDelta();
        float scaleFactor = 0.4f;
        float pixels = 64;
        float movingTimerFactor = 0.2f;

        Vector2 currentPosition = new Vector2(start_x,start_y);
        Vector2 newPosition = new Vector2(x,y);

        if(positionPart.getX() == 0.0f || positionPart.getY() == 0.0f){
            System.out.println("woohooo booohooo");
        }
        if(movingTimer <= 0) {
            if (left) {

                x = x - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                //x = x - (16 * scaleFactor);
            }

            if (right) {
                x = x + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                //x = x + (16 * scaleFactor);
            }

            if (up) {
                y = y + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                //y = y + (16 * scaleFactor);
            }

            if (down) {
                y = y - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                //y = y - (16 * scaleFactor);
            }
        }
        if(animationTimer < 1){
            animationTimer += dt * 200;
            //System.out.println(animationTimer);
            animationTimer = Math.min(animationTimer, 1);
            //vector2.lerp(start_x, x, animationTimer);
            //Vector2 pos = lerpT(currentPosition, newPosition, animationTimer);
            Vector2 pos = currentPosition.cpy();
            if(currentPosition.dst(newPosition) >= 64) {
                pos = currentPosition.lerp(newPosition, dt);
            }
            //Vector2 pos = currentPosition.lerp(newPosition, animationTimer);


//            positionPart.setX(pos.x);
//            positionPart.setY(pos.y);

            System.out.println("pos x is : " + pos.x + " --- " + "pos y is: " + pos.y);
        }
        else {
            movingTimer -= dt;
        }

        positionPart.setX(x);
        positionPart.setY(y);

    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = getCurrentStates();
    }
}
