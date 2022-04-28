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
    }
    
    public void setY(float newY) {
        this.y = newY;
    }

    public int getDirection() {
        return direction%360;
    }

    public void setDirection(int direction) {
        this.direction = direction%360;
    }
    
    public boolean isFacing(Character c) {
        if (null == c) {
            System.out.println("Did not match any direction");
        } else switch (c) {
            case 'R':
                return direction == 0;
            case 'U':
                return direction == 90;
            case 'L':
                return direction == 180;
            case 'D':
                return direction == 270;
            default:
                System.out.println("Did not match any direction");
                break;
        }
        return false;
    }

    public void setPosition(float newX, float newY) {
        setX(newX);
        setY(newY);
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        
    }
    
}
