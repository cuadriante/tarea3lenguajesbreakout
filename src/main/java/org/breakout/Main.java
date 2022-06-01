package org.breakout;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The type Main.
 */
public class Main extends Application {
  @Override
  public void start(Stage Lobby) throws Exception {
    VentanaPrincipal.ventana(Lobby);
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    // launch(args);
    Client client = new Client(8080);
    client.send_message("hola server 1");
  }
}