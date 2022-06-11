package org.breakout;

import javafx.concurrent.Task;
import javafx.application.Platform;
import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.breakout.blockFactory.Block;

public class GameLoop {
    private boolean gameIsRunning = true;
    Client client;
    private GameWindow gameWindow;
    List<Block> blockList = Collections.synchronizedList(new ArrayList<Block>());
    List<Ball> ballList = Collections.synchronizedList(new ArrayList<Ball>());
    private final PlayerBar playerBar;

    GameLoop(GameWindow gw, ArrayList<Ball> bl, ArrayList<Block> blockLst, PlayerBar pb){
        gameWindow = gw;
        ballList = bl;
        blockList = blockLst;
        playerBar = pb;
        System.out.println("wowow");
    }

    //void receiveClient(Client client);


    public void ballAnimationLoop(){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                if (gameIsRunning) {
                                    isLevelComplete(); // !Creo que esto va a dar error por editar el hilo principal
                                    // Animacion de bola
                                    moveBalls();
                                    //checkAllBallCollisions();
                                    //checkAllBallsOutOfBounds();
                                    atLeastOneBall();
                                } else{
                                    gameWindow.endGame();
                                }
                            }
                        });

                    }
                }, 0, 100);
    }

    public void atLeastOneBall() {
        boolean noBallsVisible = true;
        for(Ball b : ballList){
            if (b.getShape().isVisible()){
                gameIsRunning = true;
                noBallsVisible = false;
                break;
            }
        }
        if (gameWindow.getLife() > 0){
            if (noBallsVisible){
                if (!gameWindow.ballRecycle()){
                    gameWindow.newBall();
                }
            }

        } else {
          //  gameIsRunning = false;
        }
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

    public void checkAllBallCollisions(){
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            ball.checkCollision();
        }
    }

    public void moveBalls() {
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            if (ball.getVisibility()) {
                ball.move();
                gameWindow.sendMovement(ball);
            }
        }
    }
}
