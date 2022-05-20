/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.World;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import static dk.sdu.mmmi.modulemon.CommonMap.Data.Direction.*;

import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class MovingPart implements EntityPart {

    private boolean left, right, up, down;
    private float movingTimer = 0;
    private float animationTimer;
    private Vector2 newPosition = new Vector2();

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

    public void process(GameData gameData, World world, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float start_x = positionPart.getX();
        float y = positionPart.getY();
        float start_y = positionPart.getY();
        float dt = gameData.getDelta();
        float scale = 4;
        float pixels = 16 * scale;
        float movingTimerFactor = 0.001f;

        Vector2 currentPosition = new Vector2(start_x,start_y);

        if(!positionPart.getTargetPos().equals(new Vector2(0,0))) {
            newPosition.set(positionPart.getTargetPos());
        } else if(newPosition.equals(new Vector2(0,0))) {
            newPosition.set(x, y);
            animationTimer = 1;
        }

        if(animationTimer < 0.5){
            animationTimer += dt * 1.5f;
            animationTimer = Math.min(animationTimer, 1);
            Vector2 pos = currentPosition.lerp(newPosition, animationTimer);

            positionPart.setX(pos.x);
            positionPart.setY(pos.y);
            if(pos.dst(newPosition) < 0.5){
                positionPart.setX(newPosition.x);
                positionPart.setY(newPosition.y);
                positionPart.setCurrentPos(newPosition);
                animationTimer = 1;
            }
        }
        else {
            movingTimer -= dt;
        }

        if(movingTimer <= 0) {
            if (left) {

                x = x - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                positionPart.setDirection(WEST);
                positionPart.setTargetPos(x,y);
            }
            else if (right) {
                x = x + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                positionPart.setDirection(EAST);
                positionPart.setTargetPos(x,y);
            }
            else if (up) {
                y = y + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                positionPart.setDirection(NORTH);
                positionPart.setTargetPos(x,y);
            }
            else if (down) {
                y = y - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                positionPart.setDirection(SOUTH);
                positionPart.setTargetPos(x,y);
            }
        }
    }
}
