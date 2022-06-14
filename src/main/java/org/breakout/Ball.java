package org.breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.breakout.blockFactory.Block;


/**
 * Clase de la bola de Breakout.
 */
public class Ball{
    private Circle circle;
    private int id;
    private float xSpeed;
    private float ySpeed;
    private int xLimit;
    private int yLimit;
    private boolean visibility;
    private final int RADIUS = 5;
    private boolean isMoving = false;

    private GameWindow gameWindow;
/**
 * Constructor de la clasa Ball
 *
 * @param centerX Posición en el eje X de la bola
 * @param centerY Posición en el eje Y de la bola
 * @param gw
 */
    public Ball(int centerX, int centerY, float speed, GameWindow gw, int id){
        this.circle = new Circle(centerX, centerY, RADIUS);
        circle.setFill(Color.OLIVE);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.DARKOLIVEGREEN);
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.xLimit = 400;
        this.yLimit = 400;
        this.visibility = true;
        this.id = id;
        gameWindow = gw;
    }

    public Ball(){

    }

    /**
     * Verifica que la posición de ball se encuentre entre los limites del juego
     * @param x Posición de la bola en el eje X
     * @param y Posición de la bola en el eje Y
     */
    public void checkParameters(int x, int y){
        int radius = RADIUS + 10; //Por algun motivo si le pongo 10 funciona
        if (x  < 0){
            this.changeDirectionX();
        }else if(x + radius > xLimit){ //Falta poner el tamaño de la ventana
            this.changeDirectionX();
        }else if(y < 0){
            this.changeDirectionY();
        }else if(y + radius> yLimit){ //Falta poner el tamaño de la ventana
            setInvisible();
            // this.changeDirectionY();
            // this.removeBall();
        }
    }

    protected void setCenterX(int x){
        this.circle.setCenterX(x);
    }

    protected void setCenterY(int y){
        this.circle.setCenterY(y);
    }

    /**
     * Verifica si la bola toca la parte inferior de la ventana
     * @return true si la bola toca la parte inferior, false si no
     */
    public boolean isOutOfBounds() {
        int y = (int)this.circle.getCenterY();
        boolean dropped = false;
        if (y > yLimit ){ //ylimit
            dropped = true;
            //setInvisible();
            //setBallXandY(200, 200);
        }
        return dropped;
    }

    /**
     * Mueve la bola
     */
    public void move(){
        if (this.visibility){
            int x = (int)this.circle.getCenterX();
            int y = (int)this.circle.getCenterY();
            checkParameters(x, y);
            // System.out.println(xSpeed);

            setBallXandY(x, y);
            this.circle.setCenterY(y + ySpeed);
            Timeline ballMovement = new Timeline(new KeyFrame(Duration.millis(25), mover -> {
                setBallXandY(x, y);
                checkCollision();
                if (isOutOfBounds()){
                    setInvisible();
                }
            }));
            ballMovement.setCycleCount(1);
            ballMovement.play();
            gameWindow.sendPosBalls(id, x, y);
        }
    }

    public void setBallXandY(int x, int y){
        this.circle.setCenterY(y + ySpeed);
        this.circle.setCenterX(x + xSpeed);
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

    public float getSpeed(){
        return Math.abs(this.xSpeed);
    }

    /**
     * Aumenta la velocidad de la pelota
     */
    public void speedUp(){
        float speed = gameWindow.getBallSpeed();
        if (Math.abs(speed) < 10){
            float newXSpeed = (float) (this.xSpeed*1.5);
            float newYSpeed = (float) (this.ySpeed*1.5);
            this.xSpeed = newXSpeed;
            this.ySpeed = newYSpeed;
        }
    }

    /**
     * Disminuye la velocidad de la pelota
     */
    public void speedDown(){
        float speed = Math.abs(gameWindow.getBallSpeed());
        if (Math.abs(speed) > 2){
            float newXSpeed = (float) (this.xSpeed/1.5);
            float newYSpeed = (float) (this.ySpeed/1.5);
            this.xSpeed = newXSpeed;
            this.ySpeed = newYSpeed;
        }
    }



    /**
     * Establece el atributo de visibilidad a falso
     */
    public void setInvisible(){
        boolean value = false;
        this.visibility = value;
        this.circle.setVisible(value);
    }

    public boolean getVisibility(){
        return this.visibility;
    }

    public float getXSpeed(){
        return this.xSpeed;
    }

    public float getYSpeed(){
        return this.ySpeed;
    }

    public void recycle(int X, int Y){
        // System.out.println("se esta reciclando la bolita");
        float speed = gameWindow.getBallSpeed();
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.circle.setCenterX(X);
        this.circle.setCenterY(Y);
        // System.out.println(X + " " + Y );
        this.circle.setVisible(true);
        gameWindow.client.add_ball();
        this.visibility = true;
    }

    /**
     * Verifica si la bola colisiona con la barra del jugador. Si 
     * lo hace, cambia la dirección de la bola
     * @param playerBar Barra del jugador
     */
    public void checkPlayerBarCollision(PlayerBar playerBar) {
        if(this.circle.getBoundsInParent().intersects( playerBar.getShape().getBoundsInParent()) ){
            this.changeDirectionY();
        }
    }
    
    public void checkCollision() {
        for(Block b : gameWindow.getBlockList()){
            if(b.getShape().isVisible() && this.circle.getBoundsInParent().intersects(b.getShape().getBoundsInParent()) ){
                this.changeDirectionY();
                gameWindow.breakBlock(b);
                b.getShape().setVisible(false);
                activatePower(b.getType());
                gameWindow.updatePuntos();
            }
        }
        if(this.circle.getBoundsInParent().intersects(gameWindow.getPlayerBar().getShape().getBoundsInParent()) ){
            this.changeDirectionY();
        }
    }

    public void activatePower(int type) {
        // System.out.println(type);
        switch (type) {
            case (-1) -> {}
            case (0) -> gameWindow.speedUpBalls();
            case (1) -> gameWindow.biggerPlayerbar();
            case (2) -> gameWindow.smallerPlayerbar();
            case (3) -> gameWindow.speedDownBalls();
            case (4) -> gameWindow.newLife();
            case (5) -> gameWindow.newBall();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }

    public void setInitialSpeed(float speed){
        this.xSpeed = speed;
        this.ySpeed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}