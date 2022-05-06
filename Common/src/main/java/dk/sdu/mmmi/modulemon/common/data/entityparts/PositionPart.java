/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.common.data.Direction;
import static dk.sdu.mmmi.modulemon.common.data.Direction.SOUTH;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.World;

/**
 *
 * @author Alexander
 */
public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private Direction direction; // facing direction in comparison to the unit circle
    private Vector2 currentPos = new Vector2();
    private Vector2 targetPos = new Vector2();
    private Vector2 beforeMovePos = new Vector2();

    public PositionPart(float x, float y) {
        this.x = x;
        this.y = y;
        this.direction = SOUTH;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setTargetPos(float newX, float newY){
        targetPos.set(newX, newY);
    }

    public void setTargetPos(Vector2 targetPos) {
        this.targetPos = targetPos;
    }

    public Vector2 getTargetPos(){
        return targetPos;
    }

    public void setPosition(float newX, float newY) {
        setX(newX);
        setY(newY);
    }

    @Override
    public void process(GameData gameData, World world, Entity entity) {
        
    }

    public Vector2 getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2 newPosition) {
        this.currentPos = newPosition;
    }
}
