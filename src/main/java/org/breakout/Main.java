package org.breakout;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.breakout.GameLoop;

/**
 * The type Main.
 */
// public class Main extends Application implements EventHandler<ActionEvent>{
public class Main extends Application {
  final int STAGE_WIDTH = 400;
  final int STAGE_HEIGHT = 400;


  public static void main(String[] args) {
    // Client client = new Client(8080);
    // client.send_message("hola server 1");
    launch(args);
  }

  @Override
  public void start(Stage Lobby) throws Exception {
    GameWindow gw = new GameWindow(Lobby);
  }



}