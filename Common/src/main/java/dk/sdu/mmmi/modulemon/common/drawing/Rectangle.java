package dk.sdu.mmmi.modulemon.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rectangle {
    protected float width;
    protected float height;
    protected float x;
    protected float y;

    protected float borderWidth;
    protected Color borderColor;
    protected Color fillColor;

    public Rectangle(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //Defaults
        borderWidth = 2;
        borderColor = Color.BLACK;
        fillColor = Color.WHITE;
    }

    public void draw(ShapeRenderer shapeRenderer, float dt){
        DrawingUtils.borderedRect(shapeRenderer,
                x,y,
                width,height,
                borderColor,
                fillColor,
                borderWidth);
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(Position pos){
        this.x = pos.getX();
        this.y = pos.getY();
    }
}
