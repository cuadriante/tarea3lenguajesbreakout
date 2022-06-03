package org.breakout;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The type Main.
 */
// public class Main extends Application implements EventHandler<ActionEvent>{
public class Main extends Application{
  final int STAGE_WIDTH = 400;
  final int STAGE_HEIGHT = 400;

  public static void main(String[] args) {
    // Client client = new Client(8080);
    // client.send_message("hola server 1");
    launch(args);
  }

  @Override
  public void start(Stage Lobby) throws Exception {
    // VentanaPrincipal.ventana(Lobby);
    Lobby.setTitle( "Breakout" );

    //Crear raqueta y bola
    Racket racket = new Racket(200, 350, 100, 20);
    Ball ball = new Ball(100,100,10);
    
    Pane root = new Pane();
    root.getChildren().addAll(racket.getShape(), ball.getShape());
    root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);
    
    Scene scene = new Scene(root); //se liga scene al root

    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        ball.move();
          switch(event.getCode()){
            case LEFT:
              racket.moveLeft();
              break;
            case RIGHT:
              racket.moveRight();
              break;
            default:
              break;
        }
      }
      
    });

    Lobby.setScene( scene ); //se le da al metodo el sce
    Lobby.show();
  }

}