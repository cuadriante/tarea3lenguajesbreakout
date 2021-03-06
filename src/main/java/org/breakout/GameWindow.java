package org.breakout;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.breakout.blockFactory.Block;
import org.breakout.blockFactory.BlockFactory;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.paint.Color;

/**
 * Ventana de juego
 */
public class GameWindow {

    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;
    final int PLAYER_BAR_WIDTH = 100;
    private Pane root;
    private float ballSpeed = 6;
    private int numBalls = 1;

    Client client;

    private static final Text puntosLabel = new Text();
    private static final Text levelLabel = new Text();

    private static final Text livesLabel = new Text();
    private static final Text puntos = new Text();
    private static final Text level = new Text();

    private static final Text lives = new Text();
    private final PlayerBar playerBar;
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    private ArrayList<Block> blockList = new ArrayList<Block>();
    final GameLoop gameLoop;

    /**
     * Retorna el loop de juego
     * 
     * @return game loop
     */
    public GameLoop getGameLoop() {
        return gameLoop;
    }

    /**
     * Constructor de la ventana de juego. Crea la barra del jugador,el loop de
     * juego,
     * la ventana, los labels.
     * 
     * @param Lobby
     * @throws Exception
     */
    GameWindow(Stage Lobby) throws Exception {

        client = Client.getInstance();
        playerBar = buildPlayerBar();

        gameLoop = new GameLoop(this, ballList, blockList, playerBar);

        Lobby.setTitle("Breakout");
        Lobby.setResizable(false);

        start(Lobby);
        createLabels();
        gameLoop.ballAnimationLoop();
    }

    /**
     * Inicializa la barra del jugador y el movimiento de la misma con
     * las teclas de izquierda y derecha del teclado del jugador.
     * 
     * @param Lobby Stage primario para la aplicacion
     */
    private void start(Stage Lobby) {
        root = new Pane();

        buildBlockList();
        buildBallList();
        root.getChildren().add(playerBar.getShape());

        // root.getChildren().addAll(playerBar.getShape(), ball.getShape());
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        Scene scene = new Scene(root); // se liga scene al root
        scene.setFill(Color.BURLYWOOD);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        gameLoop.toggleMoveBalls();
                        break;
                    case LEFT:
                        playerBar.moveLeft();
                        break;
                    case RIGHT:
                        playerBar.moveRight();
                        break;
                    default:
                        break;
                }
                int barPos = (int) playerBar.getShape().getX();
                client.set_paddle_position(barPos);
            }
        });

        Lobby.setScene(scene);
        Lobby.show();
    }

    /**
     * Crea la lista de bloques de acuerdo a la
     * matriz que recibe del servidor
     */
    public void buildBlockList() {
        ArrayList<int[]> blockAttributesArray = client.get_blocks();
        int id = 0;
        int x = 3;
        int y = 40;
        for (int[] blockAttributes : blockAttributesArray) {
            int row = blockAttributes[0];
            int column = blockAttributes[1];
            // int pts = blockAttributes[2];
            int power = blockAttributes[3];
            Block block = BlockFactory.buildBlock(power, x, y, id, row, column);
            blockList.add(block);
            root.getChildren().add(block.getShape());
            x += BlockFactory.getWidth() + 5;
            block.createRectangleColor(row);
            if (column == 7) {
                x = 3;
                y += BlockFactory.getHeight() + 5;
            }
        }
        // System.out.print("----");
    }

    /**
     * Hace visibles los bloques.
     */
    private void resetBlocks() {
        for (Block block : blockList) {
            block.getShape().setVisible(true);
        }
    }

    /**
     * Si el jugador tiene vidas, se crea una nueva bola. Si no, se detiene el juego
     */
    public void noBalls() {
        if (get_lives() > 0) {
            minusOneLife();
            // try {
            // TimeUnit.SECONDS.sleep(2);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            this.ballSpeed = 6;
            newBall();
        } else {
            gameLoop.stopGame();
        }

    }

    /**
     * Construye la lista de bolas
     */
    private void buildBallList() {
        for (int i = 0; i < 5; i++) {
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

    /**
     * Retorna la barra del jugador
     * 
     * @return barra del jugador
     */
    public PlayerBar getPlayerBar() {
        return playerBar;
    }

    /**
     * Retorna la lista de bloques
     * 
     * @return lista de bloques
     */
    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    /**
     * Finaliza el juego cuando no quedan mas vidas
     */
    public void endGame() {
        System.exit(0);
    }

    /**
     * Hace que aparezca una bola nueva al juego. Ya sea reciclando una bola
     * que desactivada o creando una nueva.
     */
    public void newBall() {
        System.out.println("creando nueva bolita");
        int y = STAGE_HEIGHT / 2;
        int x = STAGE_WIDTH / 2;

        boolean flag = false;
        for (Ball ball : ballList) {
            if (!ball.getVisibility()) {
                ball.recycle(x, y);
                flag = true;
                break;
            }
        }
        if (!flag) {
            Ball newBall = buildBall(x, y);
            ballList.add(newBall);
            root.getChildren().add(newBall.getShape());
            client.add_ball();
        }
    }

    /**
     * Retorna una bola y lleva la cuenta de las bolas
     * 
     * @param x x
     * @param y y
     * @return bola
     */
    public Ball buildBall(int x, int y) {
        Ball ball = new Ball(x, y, this.ballSpeed, this, this.numBalls);
        this.numBalls += 1;
        return ball;
    }

    /**
     * Crea la barra de jugador
     * 
     * @return barra de jugador
     */
    public PlayerBar buildPlayerBar() {
        int xPos = 200;
        PlayerBar playerBar = new PlayerBar(xPos, 350, PLAYER_BAR_WIDTH, BlockFactory.getHeight());
        client.set_paddle_position(xPos);
        client.set_paddle_width(PLAYER_BAR_WIDTH);
        return playerBar;
    }

    /**
     * Agrega una vida al jugadoor
     */
    public void newLife() {
        client.add_life();
        int life = client.get_lives();
        String vidas = Integer.toString(life);
        lives.setText(vidas);
    }

    /**
     * Retorna las vidas actuales mediante el servidor
     * 
     * @return vidas
     */
    public int get_lives() {
        return client.get_lives();
    }

    /**
     * Resta una vida y actualiza el servidor
     */
    public void minusOneLife() {
        client.take_life();
        int life = client.get_lives();
        String vidas = Integer.toString(life);
        lives.setText(vidas);
    }

    /**
     * Aumenta la velocidad de las bolas y envia el dato al server
     */
    public void speedUpBalls() {
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            ball.speedUp();
        }
        Ball ball = ballList.get(0);
        this.ballSpeed = ball.getSpeed();
    }

    /**
     * Reduce la velocidad de las bolas y envia el dato al server
     */
    public void speedDownBalls() {
        Iterator<Ball> itr = ballList.iterator();
        while (itr.hasNext()) {
            Ball ball = itr.next();
            ball.speedDown();
        }
        Ball ball = ballList.get(0);
        float xSpeed = ball.getXSpeed();
        float ySpeed = ball.getYSpeed();
        // client.set_ball_speed_x(xSpeed);
        // client.set_ball_speed_y(ySpeed);
    }

    /**
     * Actualiza el puntaje mediante el servidor
     * y lo despliega en la interfaz
     */
    public void updatePuntos() {
        int pts = client.get_score();
        String puntaje = Integer.toString(pts);
        puntos.setText(puntaje);
    }

    /**
     * Pasa al siguiente nivel cuando el cliente lo indica
     */
    public void nextLevel() {
        client.level_up();
        int lvl = client.get_level();
        String niv = Integer.toString(lvl);
        level.setText(niv);
        setUpNextLevel();
    }

    /**
     * Envia la posicion de la bola al servidor
     * 
     * @param ballId id de la bola
     * @param xPos   posicion en el eje x de la bola
     * @param yPos   posicion en el eje y de la bola
     */
    public void sendPosBalls(int ballId, int xPos, int yPos) {
        client.setPosX(ballId, xPos);
        client.setPosY(ballId, yPos);
    }

    /**
     * Duplica el tama??o de la barra del jugador
     */
    public void biggerPlayerbar() {
        playerBar.makeBigger();
        int barWidth = (int) playerBar.getShape().getWidth();
        client.set_paddle_width(barWidth);
    }

    /**
     * Reduce a la mitad el tama??o de la barra del jugador
     */
    public void smallerPlayerbar() {
        playerBar.makeSmaller();
        int barWidth = (int) playerBar.getShape().getWidth();
        client.set_paddle_width(barWidth);
    }

    public static void terminarJuego(char condicion) {
        // Llamar ventana game over
        // GameStage.close();
        // stagePrincipal.show();
    }

    /**
     * Elimina todas las bola y vuelve a crear la matriz de bloques
     * y una unica bola para pasar al siguiente nivel de
     * juego
     */
    private void setUpNextLevel() {
        for (Ball ball : ballList) {
            ball.setInvisible();
            int id = ball.getId();
            client.hide_ball(id);
        }
        newBall();
        speedUpBalls();
        resetBlocks();
    }

    /**
     * Crea los labels de puntaje, nivel y vidas que aparecen en la interfaz.
     */
    private void createLabels() {

        double fontSize = 15;
        FontWeight fontWeight = FontWeight.BOLD;
        Font numberFont = Font.font("Biome", fontWeight, fontSize);

        double labelFontSize = 10;
        FontWeight labelFontWeight = FontWeight.BOLD;
        Font labelFont = Font.font("Biome", labelFontWeight, labelFontSize);

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

    /**
     * Destruye el bloque indicado.
     * 
     * @param b bloque a destruir
     */
    public void breakBlock(Block b) {
        int row = b.getRow();
        int column = b.getColum();
        client.destroy_block(row, column);
    }

    /**
     * Retorna la velocidad actual de la bola
     * 
     * @return velocidad de la bola
     */
    public float getBallSpeed() {
        return ballSpeed;
    }

    /**
     * Cambia el valor de la velocidad de la bola al especificado
     * 
     * @param speed nuevo valor de velocidad de la bola
     */
    public void setBallSpeed(float speed) {
        this.ballSpeed = speed;
    }

}
