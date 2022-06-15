package org.breakout.spectator;

import javafx.application.Application;
import javafx.stage.Stage;
import org.breakout.GameWindow;

/**
 * Main de espectador.
 */
// public class Main extends Application implements EventHandler<ActionEvent>{
public class SpectatorMain extends Application {
    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;

    public static void main(String[] args) {
        // SpectatorClient client = new SpectatorClient(8080);
        // client.test_communication();
        // System.out.println("piplup");
        launch(args);
    }

    /**
     * Inicializa la ventana del espectador
     * @param Lobby the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage Lobby) throws Exception {
        SpectatorWindow gw = new SpectatorWindow(Lobby);
        SpectatorClient client = new SpectatorClient(8080, gw);
        client.start();
        
    }

}