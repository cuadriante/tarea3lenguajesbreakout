package org.breakout;

import javafx.concurrent.Task;
import javafx.application.Platform;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.breakout.blockFactory.Block;

public class GameLoop {
    private boolean LOOP = true;
    private boolean gameStatus = true;
    Client client;
    private final GameWindow gameWindow;
    private ArrayList<Block> blockList;
    private ArrayList<Ball> ballList;
    private final PlayerBar playerBar;

    GameLoop(GameWindow gw, ArrayList<Ball> bl, ArrayList<Block> blockLst, PlayerBar pb){
        gameWindow = gw;
        ballList = bl;
        blockList = blockLst;
        playerBar = pb;
        System.out.println("wowow");
    }

    //void receiveClient(Client client);

    void loop(){
        // *COMENTÉ ESTO JEJE
        // gameWindow.getBall().move();
        while(LOOP) {
            //
            try {
                System.out.println("wowo");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOOP = false;
        }
        //gameWindow.getBall().move();
        //}
        // move ball
        // loop. por ahora
    }

    public void ballAnimationLoop(){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                if (gameStatus) {
                                    isLevelComplete(); // !Creo que esto va a dar error por editar el hilo principal
                                    // Animacion de bola
                                    moveBalls();
                                } else{
                                    gameWindow.endGame();
                                }
                            }
                        });

                    }
                }, 0, 100);
    }

    private void atLeastOneBall() {
        for(Ball b : ballList){
            if (b.getShape().isVisible()){
                gameStatus = true;
                break;
            }
        }
        gameStatus = false;
        //newBall();
    }

    /**
     * Crea una bola nueva en el juego
     */
    private void newBall(){
        gameWindow.newBall();
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

    public void moveBalls() {
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            ball.move();
            ball.checkCollision();
            if (ball.dropBall()) {
                ball.setInvisible();
                atLeastOneBall();
            }
        }
    }

}
