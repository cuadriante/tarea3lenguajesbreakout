package org.breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase del rectangulo de juego.
 */

public class PlayerBar {
    private Rectangle rectangle;
    float center;
    private final int SPEED = 10;

    /**
     * Constructor de la barra de juego
     * @param posX Posicion en el eje X de la barra de juego
     * @param posY Posicion en el eje Y de la barra de juego
     * @param width Ancho de la barra juego
     * @param height Altura de la barra de juego
     */
    public PlayerBar(int posX, int posY, int width, int height){
        // this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setX(posX);
        this.rectangle.setY(posY);
        rectangle.setArcWidth(15.0);
        rectangle.setArcHeight(10.0);
        rectangle.setFill(Color.SALMON);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.CRIMSON);
        setCenter();
    }

    private void setCenter(){
        this.center = (float) (this.rectangle.getX() + this.rectangle.getWidth()/2);
    }

    /**
     * Retorna el atributo rectangulo
     * @return rectangulo
     */
    public Rectangle getShape(){
        return this.rectangle;
    }

    public void setPos(int xPos){
        this.rectangle.setX(xPos);
        setCenter();
    }

    /**
     * Mueve la raqueta hacia la derecha
     */
    public void moveLeft(){
        double x = rectangle.getX();
        if(this.center > 0){
            this.rectangle.setX(x - SPEED);
            this.setCenter();
        }
    }

    /**
     * Mueve la raqueta hacia la izquierda
     */
    public void moveRight(){
        double x = rectangle.getX();
        if (this.center < 400){
            this.rectangle.setX(x + SPEED);
            this.setCenter();
        }
    }
    /**
     * Hace la raqueta de juego más ancha
     */
    public void makeBigger(){
        if (this.rectangle.getWidth() < 200){
        int extraWidth = (int)(rectangle.getWidth()*0.4);
        rectangle.setWidth(rectangle.getWidth() + extraWidth);
        }
    }
    /**
     * Hace la barra de juego más angosta
     */
    public void makeSmaller(){
        if ( this.rectangle.getWidth() > 50){
            int extraWidth = (int)(rectangle.getWidth()*0.4);
            rectangle.setWidth(rectangle.getWidth() - extraWidth);
        }
    }
}