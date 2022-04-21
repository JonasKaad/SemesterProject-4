/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private int direction; // facing direction in comparison to the unit circle

    public PositionPart(float x, float y) {
        this.x = x;
        this.y = y;
        this.direction = 270;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float newX) {
        this.x = newX;
        if (newX > x) {
            direction = 0;
        } else if (newX < x) {
            direction = 180;
        }
    }
    
    public void setY(float newY) {
        this.y = newY;
        if (newY > y) {
            direction = 90;
        } else if (newY < y) {
            direction = 270;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public boolean isFacing(Character c) {
        boolean result = false;
        switch (c) {
            case 'R': //Right
               result = this.direction%360 == 0;
               break;
            case 'U': //Up
                result = this.direction%360 == 90;
                break;
            case 'L': //Left
                result = this.direction%360 == 180;
                break;
            case 'D': //Down
                result = this.direction%360 == 270;
                break;
            default: System.out.println(("Did not match any direction"));
        }
        return result;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void process(GameData gameData, Entity entity) {
    }
    
    
    
    
}
