/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private boolean left, right, up, down;
    private float movingTimer = 0;
    private float animationTimer;
    Vector2 vector2 = new Vector2();


    public MovingPart() {
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


        // turning
        if(movingTimer <= 0) {
            if (left) {
                x = x - pixels;
                movingTimer = movingTimerFactor;
                animationTimer = 0;
                //x = x - (16 * scaleFactor);
            }

            if (right) {
                x = x + pixels;
                movingTimer = movingTimerFactor;
                animationTimer = 0;

                //x = x + (16 * scaleFactor);
            }

            if (up) {
                y = y + pixels;
                movingTimer = movingTimerFactor;
                animationTimer = 0;

                //y = y + (16 * scaleFactor);
            }

            if (down) {
                y = y - pixels;
                movingTimer = movingTimerFactor;
                animationTimer = 0;

                //y = y - (16 * scaleFactor);
            }
        }
        if(animationTimer <= 0){
            animationTimer += dt * 1;
            animationTimer = Math.min(animationTimer, 1);
            //vector2.lerp(start_x, x, animationTimer);
            positionPart.setX(x);
            positionPart.setY(y);
        }
        else {
            movingTimer -= dt;
        }

        // set position
        /*
        x += x * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        }
        else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += x * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        }
        else if (y < 0) {
            y = gameData.getDisplayHeight();
        }
         */

        positionPart.setX(x);
        positionPart.setY(y);

    }

}
