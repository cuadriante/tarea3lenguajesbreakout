package org.breakout;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameWindow {

    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;
    private final PlayerBar playerBar = new PlayerBar(200, 350, 100, 20);
    private final Ball ball = new Ball(100, 100, 10);
    final GameLoop GAME_LOOP = new GameLoop(this, ball); // creo que esto es un singleton xd


    GameWindow(Stage Lobby) throws Exception {
        Lobby.setTitle("Breakout");
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
        Pane root = new Pane();
        root.getChildren().addAll(playerBar.getShape(), ball.getShape());
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        Scene scene = new Scene(root); //se liga scene al root

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //ball.move();
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

    public Ball getBall(){
        return this.ball;
    }

    public void setBlockList(){

    }

    public void endGame(){

    }

    public GameWindow getGameWindow(){
        return this;
    }

}
