/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private boolean left, right, up, down;

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
        float y = positionPart.getY();
        float dt = gameData.getDelta();
        int scaleFactor = 1;

        // turning
        if (left) {
            x = x - (16 * scaleFactor);
        }

        if (right) {
            x = x + (16 * scaleFactor);
        }

        if (up) {
            y = y + (16 * scaleFactor);
        }

        if (down) {
            y = y - (16 * scaleFactor);
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
