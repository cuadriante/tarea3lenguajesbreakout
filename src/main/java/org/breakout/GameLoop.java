package org.breakout;

import javafx.concurrent.Task;
import org.breakout.GameWindow;
import org.ietf.jgss.GSSManager;

public class GameLoop {
    private boolean LOOP = true;
    private boolean gameStatus = true;
    Client client;
    private final GameWindow gameWindow;
    private final Ball ball;

    GameLoop(GameWindow gw, Ball b){
        gameWindow = gw;
        ball = b;
        System.out.println("wowow");
    }

    //void receiveClient(Client client);

    void loop(){
        gameWindow.getBall().move();
       while(LOOP) {
           //
           try {
               System.out.println("wowo");
               Thread.sleep(4000);
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
                        try {
                           ball.move();
                            Thread.sleep(200);
                        } catch (Exception e) {
                            e.printStackTrace();
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
}
