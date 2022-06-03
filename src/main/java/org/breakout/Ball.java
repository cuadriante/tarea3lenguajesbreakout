package org.breakout;

import javafx.scene.shape.Circle;

public class Ball{
    private Circle circle;
    private int xSpeed = -10;
    private int ySpeed = 10;
    private int xLimit;
    private int yLimit;

    public Ball(int centerX, int centerY, int radius){
        this.circle = new Circle(centerX, centerY, radius);
        this.xLimit = 400;
        this.yLimit = 400;
    }


    public void checkParameters(int x, int y){
        if (x < 0){
            this.changeDirectionX();
        }else if(x > xLimit){ //Falta poner el tamaño de la ventana
            this.changeDirectionX();
        }else if(y < 0){
            this.changeDirectionY();
        }else if(y > yLimit){ //Falta poner el tamaño de la ventana
            this.changeDirectionY();
        }
    }

    public void move(){
        int x = (int)this.circle.getCenterX();
        int y = (int)this.circle.getCenterY();
        checkParameters(x, y);
        this.circle.setCenterX(x + xSpeed);
        this.circle.setCenterY(y + ySpeed);
    }

    private void changeDirectionY() {
        this.ySpeed = -1*this.ySpeed;
    }

    private void changeDirectionX() {
        this.xSpeed = -1*this.xSpeed;
    }

    public Circle getShape(){
        return this.circle;
    }
}