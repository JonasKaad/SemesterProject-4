/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.modulemon.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.modulemon.CommonMap.IMapView;
import dk.sdu.mmmi.modulemon.common.animations.BaseAnimation;
import dk.sdu.mmmi.modulemon.common.data.Entity;
import dk.sdu.mmmi.modulemon.common.data.GameData;

import java.util.ArrayList;

public class MovingPart extends BaseAnimation implements EntityPart {

    private boolean left, right, up, down;
    private float movingTimer = 0;
    private float animationTimer;
    private Vector2 newPosition = new Vector2();
    private static IMapView mapView;


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

    public void process(GameData gameData, Entity entity) {
        if(gameData.isPaused()) return;
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float start_x = positionPart.getX();
        float y = positionPart.getY();
        float start_y = positionPart.getY();
        float dt = gameData.getDelta();
        float scaleFactor = 0.4f;
        float pixels = 64;
        float movingTimerFactor = 0.001f;

        Vector2 currentPosition = new Vector2(start_x,start_y);


        if(movingTimer <= 0) {
            newPosition.set(x,y);
            if (left) {

                x = x - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                positionPart.setDirection(180);
                if(newPosition.x < mapView.getMapLeft() || mapView.isCellBlocked(newPosition.x, newPosition.y)){
                    newPosition.x = currentPosition.x;
                }
            }
            else if (right) {
                x = x + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                positionPart.setDirection(0);
                if(newPosition.x > mapView.getMapRight() - 64 + 8 || mapView.isCellBlocked(newPosition.x, newPosition.y)){
                    newPosition.x = currentPosition.x;
                }
            }
            else if (up) {
                y = y + pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                positionPart.setDirection(90);
                if(newPosition.y > mapView.getMapTop() - 64 || mapView.isCellBlocked(newPosition.x, newPosition.y)){
                    newPosition.y = currentPosition.y;
                }
            }
            else if (down) {
                y = y - pixels;

                movingTimer = movingTimerFactor;
                animationTimer = 0;

                newPosition.set(x, y);
                positionPart.setDirection(270);                
                if(newPosition.y < mapView.getMapBottom() || mapView.isCellBlocked(newPosition.x, newPosition.y)){
                    newPosition.y = currentPosition.y;
                }
            }
        }
        if(animationTimer < 0.5){
            animationTimer += dt * 1.5;
            animationTimer = Math.min(animationTimer, 1);
            Vector2 pos = currentPosition.lerp(newPosition, animationTimer);

            positionPart.setX(pos.x);
            positionPart.setY(pos.y);
            if(pos.dst(newPosition) < 0.5){
                positionPart.setX(newPosition.x);
                positionPart.setY(newPosition.y);
            }
        }
        else {
            movingTimer -= dt;
        }
    }

    @Override
    public void update(GameData gameData) {
        super.tick();
        float[] states = getCurrentStates();
    }

    public void setMapView(IMapView mapView){
        while(MovingPart.mapView == null){
            MovingPart.mapView = mapView;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeMapView(IMapView mapView){
        MovingPart.mapView = null;
    }
}
