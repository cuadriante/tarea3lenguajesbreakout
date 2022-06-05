package org.breakout.blockFactory;

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
        setCenter();
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

}

enum Color
{
    GREEN, YELLOW, ORANGE, RED
}

