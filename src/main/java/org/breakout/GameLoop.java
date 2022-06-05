package org.breakout;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameLoop {
    private boolean LOOP = true;
    private boolean gameStatus = true;
    Client client;
    private final GameWindow gameWindow;
    // private final Ball ball;
    private List<Ball> ballList;
    private final PlayerBar playerBar;

    GameLoop(GameWindow gw, ArrayList<Ball> bl, PlayerBar pb){
        gameWindow = gw;
        ballList = bl;
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
        Task<Void> ballMovementAnimation;
        ballMovementAnimation = new Task<>() {
            @Override
            public Void call() {
                if (gameStatus) {
                    Iterator<Ball> itr = ballList.iterator();
                    while(itr.hasNext()){
                        Ball ball = itr.next();
                        try {
                            ball.move();
                            ball.checkCollision();
                            if(ball.dropBall()){
                                ball.getShape().setVisible(false);
                                ballList.remove(ball);
                                //gameWindow.removeBall(ball); //! Da error porque borro algo de javafx desde un hilo diferente al principal
                                atLeastOneBall();
                            }
                            Thread.sleep(200);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else{
                    gameWindow.endGame();
                }
                return null;
            }

        };
        ballMovementAnimation.setOnSucceeded(event -> {
            if (ballMovementAnimation.isDone()){
                ballAnimationLoop();
            }
        });
        new Thread(ballMovementAnimation).start();
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
}
