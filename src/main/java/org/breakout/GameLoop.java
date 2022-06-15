package org.breakout;

import javafx.concurrent.Task;
import javafx.application.Platform;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.breakout.blockFactory.Block;

/**
 * Loop de juego
 */
public class GameLoop {
    private boolean gameIsRunning = true;
    private boolean moveBalls = false;
    Client client;
    private GameWindow gameWindow;
    List<Block> blockList = Collections.synchronizedList(new ArrayList<Block>());
    List<Ball> ballList = Collections.synchronizedList(new ArrayList<Ball>());
    private final PlayerBar playerBar;

    /**
     * Constructor que recibe la ventana de juego, la lista de bolas,
     * la lista de bloques y la barra de jugador
     * @param gw ventana de juego
     * @param bl lista de bolas
     * @param blockLst lista de bloques
     * @param pb barra de jugador
     */
    GameLoop(GameWindow gw, ArrayList<Ball> bl, ArrayList<Block> blockLst, PlayerBar pb){
        gameWindow = gw;
        ballList = bl;
        blockList = blockLst;
        playerBar = pb;
        System.out.println("wowow");
    }

    //void receiveClient(Client client);


    /**
     * Loop que se encarga de enviar a la funcion de movimiento
     * de bola o de complecion de nivel
     */
    public void ballAnimationLoop(){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                if (gameIsRunning) {
                                    isLevelComplete();
                                    // Animacion de bola
                                    if(moveBalls){
                                        moveBalls();
                                    }
                                    //checkAllBallCollisions();
                                    //checkAllBallsOutOfBounds();
                                    if (!atLeastOneBall()){
                                        System.out.println("No hay al menos una bola");
                                        gameWindow.noBalls();
                                    }
                                } else{
                                    gameWindow.endGame();
                                }
                            }
                        });

                    }
                }, 0, 100);
    }

    /**
     * Retorna true si al menos hay una bola en juego, retorna false
     * si no hay bolas en juego.
     * @return valor de verdad
     */
    public boolean atLeastOneBall() {
        boolean isBallVisible = false;
        for(Ball b : ballList){
            if (b.getShape().isVisible()){
                gameIsRunning = true;
                isBallVisible = true;
                break;
            }
        }
        return isBallVisible;
    }

    /**
     * Verifica si ya se pasó el nivel reivisando el número
     * de bloques no destruidos
     */
    private void isLevelComplete(){
        boolean flag = false;
        for (Block block : blockList){
            if(block.getShape().isVisible()){
                flag = true;
                break;
            }
        }
        if(!flag){
            gameWindow.nextLevel();
        }
    }

    /**
     * Verifica para todas las bolas en la lista
     * si se encuentran fuera de la ventana de juego
     */
    public void checkAllBallsOutOfBounds(){
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            if (ball.isOutOfBounds()) {
                //System.out.println("se cayo la bola");
                ball.setInvisible();
            }
        }
    }

    /**
     * Verifica para todas las bolas en la lista
     * si han colisionado
     */
    public void checkAllBallCollisions(){
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            ball.checkCollision();
        }
    }

    /**
     * Mueve todas las bolas que sean visibles en el momento de ejecucion de la funcion
     */
    public void moveBalls() {
        if(moveBalls){
            for(Ball ball : ballList){
                if(ball.getVisibility()){
                    ball.move();
                }
            }
        }
    }

    /**
     * Toggle para el manejo del movimiento de las bolas
     */
    public void toggleMoveBalls(){
        moveBalls = !moveBalls;
    }

    /**
     * Cambia el estado de juego a falso.
     */
    public void stopGame() {
        this.gameIsRunning = false;
    }
}
