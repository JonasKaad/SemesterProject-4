package dk.sdu.mmmi.modulemon.BattleScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class PersonaPosition extends Position{
    private float driftLevel;
    private float animationSpeed;
    private float xTime;
    private float yTime;

    private float driftedX;
    private float driftedY;
    public PersonaPosition(float x, float y) {
        super(x, y);
        this.driftedX = x;
        this.driftedY = y;
        driftLevel = 3000;
        animationSpeed = 0.001f;
        Random r = new Random();
        xTime = randomFloat(r, -10, 10);
        yTime = randomFloat(r, -10, 10);
    }

    @Override
    public void updatePosition(Actor actor) {
        updateDrift();
        actor.setPosition(this.driftedX, this.driftedY);
    }

    @Override
    public float getX() {
        updateDrift();
        return this.driftedX;
    }

    @Override
    public float getY() {
        updateDrift();
        return this.driftedY;
    }

    public float getRealX() {
        return super.getX();
    }

    public float getRealY() {
        return super.getY();
    }

    private void updateDrift(){
        driftedX = getDriftLevel(super.getX(), xTime);
        driftedY = getDriftLevel(super.getY(), yTime);
        float dt = Gdx.graphics.getDeltaTime();

        xTime += this.animationSpeed * dt;
        yTime += this.animationSpeed * dt;
    }

    private float getDriftLevel(float value, float time){
        return (float) (value + ( Math.sin(this.driftLevel * time) + Math.sin(Math.PI*time) ));
    }

    private float randomFloat(Random random, int min, int max){
        return min + random.nextFloat() * (max - min);
    }
}
