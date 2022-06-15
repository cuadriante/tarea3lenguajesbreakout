package org.breakout.spectator;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.breakout.Ball;
import org.breakout.Client;
import org.breakout.GameLoop;
import org.breakout.PlayerBar;
import org.breakout.blockFactory.Block;
import org.breakout.blockFactory.BlockFactory;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.paint.Color;

import org.breakout.PlayerBar;

/**
 * Ventana de espectador.
 */
public class SpectatorWindow {

    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;
    // SpectatorClient client = new SpectatorClient(8080);
    private Pane root;
    private int numBalls = 1;
    private static final Text puntosLabel = new Text();
    private static final Text levelLabel = new Text();
    private static final Text livesLabel = new Text();
    private static final Text puntos = new Text();
    private static final Text level = new Text();
    private static final Text lives = new Text();
    private final PlayerBar playerBar;
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    private ArrayList<Block> blockList = new ArrayList<Block>();

    /**
     * Constructor de la ventana de espectador
     * @param Lobby stage de la aplicacion
     * @throws Exception
     */
    public SpectatorWindow(Stage Lobby) throws Exception {
        playerBar = new PlayerBar(200, 350, BlockFactory.getWidth(), BlockFactory.getHeight());

        Lobby.setTitle("Breakout: Spectator");
        Lobby.setResizable(false);

        start(Lobby);
        createLabels();
    }

    /**
     * Retorna una bola y lleva la cuenta de las bolas
     * @param x x
     * @param y y
     * @return bola
     */
    public Ball buildBall(int x, int y) {
        Ball ball = new Ball(x, y, numBalls);
        this.numBalls += 1;
        return ball;
    }

    private void buildBallList() {
        for(int i = 0; i < 5; i++){
            Ball ball = buildBall(STAGE_WIDTH - 100, STAGE_HEIGHT - 180);
            ball.setInvisible();
            ballList.add(ball);
        }
        Ball ball = ballList.get(0);
        ball.setVisible();
        for (Ball element : ballList) {
            root.getChildren().add(element.getShape());
        }
    }

    public void setBallPosX(int id, int posX){
        for(Ball ball : ballList){
            if(ball.getId() == id){
                ball.getShape().setCenterX(posX);
            }
        }
    }

    public void setBallPosY(int id, int posY){
        for(Ball ball : ballList){
            if(ball.getId() == id){
                ball.getShape().setCenterY(posY);
            }
        }
    }

    public void hideBall(int ballId){
        for(Ball ball : ballList){
            if(ball.getId() == ballId){
                ball.setInvisible();
            }
        }
    }

    /**
     * cambia la posicion de la barra de juego
     * @param xPos posicion en x
     */
    public void setPlayerBarPos(int xPos){
        playerBar.setPos(xPos);
    }

    public void addBall(int id){
        System.out.println(id);
        for(Ball ball : ballList){
            if(ball.getId() == id){
                ball.setVisible();
                ball.setBallXandY(200, 200);
            }
        }
    }

    /**
     * Inicializa la barra del jugador y el movimiento de la misma con
     * las teclas de izquierda y derecha del teclado del jugador.
     * @param Lobby Stage primario para la aplicacion
     */
    private void start(Stage Lobby) {
        root = new Pane();

        root.getChildren().add(playerBar.getShape());
        buildBallList();


        // root.getChildren().addAll(playerBar.getShape(), ball.getShape());
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        Scene scene = new Scene(root); //se liga scene al root
        scene.setFill(Color.BURLYWOOD);

        int barPos = (int) playerBar.getShape().getX();
        // client.set_paddle_position(barPos);

        Lobby.setScene(scene);
        Lobby.show();
    }

    /**
     * Crea los labels de puntaje, nivel y vidas que aparecen en la interfaz.
     */
    private void createLabels() {

        double fontSize = 15;
        FontWeight fontWeight = FontWeight.BOLD;
        Font numberFont = Font.font("Biome", fontWeight,fontSize);

        double labelFontSize = 10;
        FontWeight labelFontWeight = FontWeight.BOLD;
        Font labelFont = Font.font("Biome", labelFontWeight,labelFontSize);

        // int pts = client.get_score();
        int pts = 0;
        String puntaje = Integer.toString(pts);
        puntos.setText(puntaje);
        puntos.setX(10);
        puntos.setY(30);
        puntos.setFill(Color.SADDLEBROWN);
        puntos.setFont(numberFont);

        puntosLabel.setText("SCORE");
        puntosLabel.setX(10);
        puntosLabel.setY(15);
        puntosLabel.setFill(Color.SADDLEBROWN);
        puntosLabel.setFont(labelFont);

        // int lvl = client.get_level();
        int lvl = 1;
        String nivelString = Integer.toString(lvl);
        level.setText(nivelString);
        level.setX(100);
        level.setY(30);
        level.setFill(Color.SADDLEBROWN);
        level.setFont(numberFont);

        levelLabel.setText("LEVEL");
        levelLabel.setX(100);
        levelLabel.setY(15);
        levelLabel.setFill(Color.SADDLEBROWN);
        levelLabel.setFont(labelFont);

        // int life = client.get_lives();
        int life = 3;
        String livesString = Integer.toString(life);
        lives.setText(livesString);
        lives.setX(380);
        lives.setY(30);
        lives.setFill(Color.SADDLEBROWN);
        lives.setFont(numberFont);

        livesLabel.setText("LIVES");
        livesLabel.setX(365);
        livesLabel.setY(15);
        livesLabel.setFill(Color.SADDLEBROWN);
        livesLabel.setFont(labelFont);

        root.getChildren().add(puntos);
        root.getChildren().add(level);
        root.getChildren().add(lives);
        root.getChildren().add(livesLabel);
        root.getChildren().add(puntosLabel);
        root.getChildren().add(levelLabel);
    }

    public void setPlayerBarWidth(int width) {
        playerBar.getShape().setWidth(width);
    }




}
