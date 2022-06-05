package org.breakout;

import org.breakout.GameWindow;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * Clase de la bola de Breakout.
 */
public class Ball{
    private Circle circle;
    private int xSpeed = -10;
    private int ySpeed = 10;
    private int xLimit;
    private int yLimit;
    private final int RADIUS = 5;

    private GameWindow gameWindow;
    private int life;
/**
 * Constructor de la clasa Ball
 *
 * @param centerX Posición en el eje X de la bola
 * @param centerY Posición en el eje Y de la bola
 * @param gw
 */
    public Ball(int centerX, int centerY, GameWindow gw){
        this.circle = new Circle(centerX, centerY, RADIUS);
        circle.setFill(Color.OLIVE);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.DARKOLIVEGREEN);
        this.xLimit = 400;
        this.yLimit = 400;
        gameWindow = gw;
        life = 1;
    }

    /**
     * Verifica que la posición de ball se encuentre entre los limites del juego
     * @param x Posición de la bola en el eje X
     * @param y Posición de la bola en el eje Y
     */
    public void checkParameters(int x, int y){
        int radius = RADIUS + 10; //Por algun motivo si le pongo 10 funciona
        if (x - radius < 0){
            this.changeDirectionX();
        }else if(x + radius > xLimit){ //Falta poner el tamaño de la ventana
            this.changeDirectionX();
        }else if(y - radius < 0){
            this.changeDirectionY();
        }else if(y + radius> yLimit){ //Falta poner el tamaño de la ventana
            // this.changeDirectionY();
            // this.removeBall();
        }
    }

    public void collision(){
       // if (!Game)
    }

    /**
     * Verifica si la bola toca la parte inferior de la ventana
     * @return true si la bola toca la parte inferior, false si no
     */
    public boolean dropBall() {
        int y = (int)this.circle.getCenterY();
        boolean droped = false;
        if (y > yLimit){
            droped = true;
        }
        return droped;
    }

    /**
     * Mueve la bola
     */
    public void move(){
        int x = (int)this.circle.getCenterX();
        int y = (int)this.circle.getCenterY();
        checkParameters(x, y);
        this.circle.setCenterX(x + xSpeed);
        this.circle.setCenterY(y + ySpeed);
    }

    /**
     * Cambia la direccion de la bola en el eje Y.
     */
    private void changeDirectionY() {
        this.ySpeed = -1*this.ySpeed;
    }

    /**
     * Cambia la direccion de la bola en el eje X.
     */
    private void changeDirectionX() {
        this.xSpeed = -1*this.xSpeed;
    }

    /**
    * Retorna el atributo circulo.
    * @return circle
    */
    public Circle getShape(){
        return this.circle;
    }

    /**
     * Aumenta la velocidad de la pelota
     */
    public void speedUp(){
        xSpeed = 2*xSpeed;
        ySpeed = 2*ySpeed;
    }
    /**
     * Disminuye la velocidad de la pelota
     */
    public void speedDown(){
        xSpeed = xSpeed/2;
        ySpeed = ySpeed/2;
    }

    /**
     * Verifica si la bola colisiona con la barra del jugador. Si 
     * lo hace, cambia la dirección de la bola
     * @param playerBar Barra del jugador
     */
    public void checkCollision(PlayerBar playerBar) {
        if(this.circle.getBoundsInParent().intersects( playerBar.getShape().getBoundsInParent()) ){
            this.changeDirectionY();
        }
    }
}