package org.breakout;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ImageInput;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * The type Ventana principal.
 */
public class VentanaPrincipal {
    /**
     * Ventana.
     *
     * @param Lobby the lobby
     * @throws FileNotFoundException the file not found exception
     */
    public static void ventana(Stage Lobby) throws FileNotFoundException, URISyntaxException {
        Lobby.setTitle( "Breakout" );
        Group root = new Group(); //se crea la ventana
        Scene scene = new Scene( root,800,750,Color.valueOf("#262934")); //se liga scene al root
        Lobby.setScene( scene ); //se le da al metodo el scene
        //Imagen de fondo

        Task<Void> ventanaPrincipal = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(7000);
                return null;
            }
        };
        ventanaPrincipal.setOnSucceeded(event -> {
            try {
                //new CargarVentanaPrincipal(root, rectanguloCreditos, Lobby, reproductorMusica);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        });
        new Thread(ventanaPrincipal).start();
        Lobby.show();
    }
}


