package org.breakout.spectator;

import javafx.application.Application;
import javafx.stage.Stage;
import org.breakout.GameWindow;

/**
 * The type Main.
 */
// public class Main extends Application implements EventHandler<ActionEvent>{
public class SpectatorMain extends Application {
    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;

    public static void main(String[] args) {


        //SpectatorClient client = new SpectatorClient(8080);
        //client.test_communication();
        System.out.println("piplup");
        launch(args);
    }

    @Override
    public void start(Stage Lobby) throws Exception {
        // GameWindow gw = new GameWindow(Lobby);
        SpectatorWindow sw = new SpectatorWindow(Lobby);


    }

}