package org.breakout;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The type Main.
 */
// public class Main extends Application implements EventHandler<ActionEvent>{
public class Main extends Application {
  final int STAGE_WIDTH = 400;
  final int STAGE_HEIGHT = 400;

  public static void main(String[] args) {
    // Client client = new Client(8080);
    // client.test_communication();

    launch(args);
  }

  /**
   *
   * @param Lobby the primary stage for this application, onto which
   * the application scene can be set.
   * Applications may create other stages, if needed, but they will not be
   * primary stages.
   * @throws Exception
   */
  @Override
  public void start(Stage Lobby) throws Exception {
    GameWindow gw = new GameWindow(Lobby);
  }

}