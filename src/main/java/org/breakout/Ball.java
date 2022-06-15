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
     * @param speed velocidad
    * @param gw ventana de juego
     * @param id id
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

    public Ball(int centerX, int centerY, int id){
        this.circle = new Circle(centerX, centerY, RADIUS);
        circle.setStrokeWidth(2);
        circle.setStroke(Color.DARKOLIVEGREEN);
        this.visibility = true;
        this.id = id;
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
            setInvisible();
            // this.changeDirectionY();
            // this.removeBall();
        }
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
            gameWindow.sendPosBalls(id, (int)this.circle.getCenterX(), (int)this.circle.getCenterY());
        }
    }

    /**
     * cambia el valor de x y y de la bola
     * @param x valor en x
     * @param y valor en y
     */
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

    /**
     * retorna la velocidad
     * @return velocidad
     */
    public float getSpeed(){
        return Math.abs(this.xSpeed);
    }

    /**
     * Aumenta la velocidad de la pelota
     */
    public void speedUp(){
        float speed = gameWindow.getBallSpeed();
        if (Math.abs(speed) < 10){
            speed = (float) (speed*1.2);
            this.xSpeed = speed;
            this.ySpeed = speed;
        }
    }

    /**
     * Disminuye la velocidad de la pelota
     */
    public void speedDown(){
        float speed = Math.abs(gameWindow.getBallSpeed());
        if (Math.abs(speed) > 2){
            speed = (float) (speed/1.2);
            this.xSpeed = speed;
            this.ySpeed = speed;
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

    /**
     * Retorna la visibilidad
     * @return visibilidad
     */
    public boolean getVisibility(){
        return this.visibility;
    }

    /**
     * Retorna la velocidad en x
     * @return velocidad en x
     */
    public float getXSpeed(){
        return this.xSpeed;
    }

    /**
     * Retorna la velocidad en y
     * @return velocidad y
     */
    public float getYSpeed(){
        return this.ySpeed;
    }

    /**
     * Vuelve a utilizar una bola ya creada que no este movilizandose ni sea visible
     * en la ventana de juego en el momento del chequeo
     * @param X posicion en el eje x en la cual aperecera la bola
     * @param Y posicion en el eje y en la cual aparecera la bola
     */
    public void recycle(int X, int Y){
        // System.out.println("se esta reciclando la bolita");
        float speed = gameWindow.getBallSpeed();
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.circle.setCenterX(X);
        this.circle.setCenterY(Y);
        System.out.println(X + " " + Y );
        this.circle.setVisible(true);
        gameWindow.client.add_ball();
        this.visibility = true;
    }

    public void setVisible(){
        this.circle.setVisible(true);
        this.visibility = true;
    }

    /**
     * Verifica si la bola colisiona con la barra del jugador o con un bloque. Si
     * lo hace, cambia la dirección de la bola
     */
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

    /**
     * Activa un "poder" que puede afectar, a la bola, a la barra de jugador, entre otros al destruir un tipo de bloque.
     * @param type tipo de bloque
     */
    public void activatePower(int type) {
        // System.out.println(type);
        switch (type) {
            case (-1) -> {}
            case (0) -> gameWindow.speedUpBalls();
            case (1) -> gameWindow.getPlayerBar().makeBigger();
            case (2) -> gameWindow.getPlayerBar().makeSmaller();
            case (3) -> gameWindow.speedDownBalls();
            case (4) -> gameWindow.newLife();
            case (5) -> gameWindow.newBall();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

    }


    /**
     * Inicializa la velocidad inicial de la bola
     * @param speed Velocidad de la bola
     */
    public void setInitialSpeed(float speed){
        this.xSpeed = speed;
        this.ySpeed = speed;
    }

    /**
     * Retorna el atributo id.
     * @return id
     */
    public int getId() {
        return id;
    }
}