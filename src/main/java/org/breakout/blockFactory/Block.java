package org.breakout.blockFactory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Block {
    private Rectangle rectangle;
    float center;
    private int id;
    private int type;
    private int color;
    private int effect;

    public Block(int posX, int posY, int width, int height){
        // this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setX(posX);
        this.rectangle.setY(posY);

    }

    abstract void construct();

    private void setCenter(){
        this.center = (float) (this.rectangle.getX() + this.rectangle.getWidth()/2);
        System.out.println(this.center);
    }

    public Rectangle getShape(){
        return this.rectangle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public void createRectangleColor(int row){
         switch (row) {
            case (0), (1) -> setRectangleColor(Color.SEAGREEN);
            case (2), (3) -> setRectangleColor(Color.LEMONCHIFFON);
            case (4), (5) -> setRectangleColor(Color.ORANGE);
            case (6), (7) -> setRectangleColor(Color.FIREBRICK);
            default -> throw new IllegalStateException("Unexpected value: " + row);
        };

    }

    public void setRectangleColor(Color col){
        rectangle.setArcWidth(15.0);
        rectangle.setArcHeight(10.0);
        setCenter();
        rectangle.setFill(col);

    }

    public void setRectangleStroke(Color stroke){
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(stroke);
    }



}


enum col
{
    GREEN, YELLOW, ORANGE, RED
}

