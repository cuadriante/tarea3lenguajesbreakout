package org.breakout;

import javafx.scene.shape.Rectangle;

public class PlayerBar {
    private Rectangle rectangle;
    float center;
    // private PhysicsEntity prueba;
    private final int SPEED = 5;

    public PlayerBar(int posX, int posY, int width, int height){
        // this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setX(posX);
        this.rectangle.setY(posY);
        setCenter();
    }

    private void setCenter(){
        this.center = (float) (this.rectangle.getX() + this.rectangle.getWidth()/2);
        System.out.println(this.center);
    }


    public Rectangle getShape(){
        return this.rectangle;
    }

    public void moveLeft(){
        double x = rectangle.getX();
        if(this.center > 0){
            this.rectangle.setX(x - SPEED);
            this.setCenter();
        }
    }

    public void moveRight(){
        double x = rectangle.getX();
        if (this.center < 400){
            this.rectangle.setX(x + SPEED);
            this.setCenter();
        }
    }
}