package org.breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameWindow {

    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;
    private Pane root;
    private final PlayerBar playerBar = new PlayerBar(200, 350, 100, 20);
    // private final Ball ball = new Ball(100, 100, 10);
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    final GameLoop GAME_LOOP = new GameLoop(this, ballList, playerBar); // creo que esto es un singleton xd


    GameWindow(Stage Lobby) throws Exception {
        Lobby.setTitle("Breakout");
        Ball ball = new Ball(100, 100, 10);
        ballList.add(ball);
        start(Lobby);
        connectToClient();
    }

    private void connectToClient() {
        GAME_LOOP.ballAnimationLoop();
        //GAME_LOOP.loop();
        //Client* client = new Client();
        //if (client->connectSocket()) {

        //    GameLoop * gameLoop = new GameLoop();
        //    scene->addItem(gameLoop);
        //    playerBar->setClientSocket(client->getClientSocket());
        //    gameLoop->receiveClient(client);
        //} else {
        //    cout << "Could not connect to server." << endl;
        //    exit;
        //}
    }

    private void start(Stage Lobby) {
        root = new Pane();
        for (Ball ball : ballList){
            root.getChildren().add(ball.getShape());
        }
        root.getChildren().add(playerBar.getShape());

        // root.getChildren().addAll(playerBar.getShape(), ball.getShape());
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        Scene scene = new Scene(root); //se liga scene al root

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        playerBar.moveLeft();
                        break;
                    case RIGHT:
                        playerBar.moveRight();
                        break;
                    default:
                        break;
                }
            }
        });

        Lobby.setScene(scene);
        Lobby.show();
    }

    void addBlock(){

    }

    // public Ball getBall(){
    //     return this.ball;
    // }

    public void setBlockList(){

    }

    public void endGame(){

    }

    public GameWindow getGameWindow(){
        return this;
    }

    public void removeBall(Ball ball){
        root.getChildren().remove(ball.getShape());
        ballList.remove(ball);
        System.out.println(ballList.size());
    }

}
