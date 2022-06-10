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

public class GameWindow {

    final int STAGE_WIDTH = 400;
    final int STAGE_HEIGHT = 400;
    private Pane root;
    // public static int pts = 0;
    // public static int lvl = 0;
    // public static int life = 3;

    Client client = new Client(8080);

    private static final Text puntosLabel = new Text();
    private static final Text levelLabel = new Text();

    private static final Text livesLabel = new Text();
    private static final Text puntos = new Text();
    private static final Text level = new Text();

    private static final Text lives = new Text();
    private final PlayerBar playerBar;
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    private ArrayList<Block> blockList = new ArrayList<Block>();
    final GameLoop GAME_LOOP;


    GameWindow(Stage Lobby) throws Exception {
        playerBar = new PlayerBar(200, 350, BlockFactory.getWidth(), BlockFactory.getHeight());
        GAME_LOOP = new GameLoop(this, ballList, blockList, playerBar); 

        client.set_paddle_width(BlockFactory.getWidth());
        int paddlePos = (int) playerBar.getShape().getX();
        client.set_paddle_position(paddlePos);
        
        Lobby.setTitle("Breakout");
        Lobby.setResizable(false);
        
        start(Lobby);
        createLabels();
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
        root = new Pane();
        
        buildBlockList();
        buildBallList();
        root.getChildren().add(playerBar.getShape());
        

        // root.getChildren().addAll(playerBar.getShape(), ball.getShape());
        root.setPrefSize(STAGE_WIDTH, STAGE_HEIGHT);

        Scene scene = new Scene(root); //se liga scene al root
        scene.setFill(Color.BURLYWOOD);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
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
                int barPos = (int) playerBar.getShape().getX();
                client.set_paddle_position(barPos);
            }
        });

        Lobby.setScene(scene);
        Lobby.show();
    }

    public void buildBlockList() { // esto se hace con la matriz del server
        int id = 0;
        int x = 3;
        int y = 40; // dejar este espacio para poner la info del jugador

        for (int row = 0; row < BlockFactory.getRows(); row++) {
            for (int col = 0; col < BlockFactory.getColumns(); col++) {
                int type = (int) (Math.random() * 6);
                Block block = BlockFactory.buildBlock(type, x, y, id);
                blockList.add(block);
                root.getChildren().add(block.getShape());
                x += BlockFactory.getWidth() + 5;
                block.createRectangleColor(row);
            }
            x = 3;
            y += BlockFactory.getHeight() + 5;
        }
    }

    /**
     * Crea la lista de bloques de acuerdo a la 
     * matriz que recibe del servidor
     */
    // public void buildBlockList(){
    //     ArrayList<int[]> blockAttributesArray = client.get_blocks();
    //     int x = 3;
    //     int y = 40;
    //     int id = 0;

    //     for (int[] blockAttributes : blockAttributesArray){
    //         int isBroken = blockAttributes[0];
    //         int row = blockAttributes[0];
    //         int column = blockAttributes[0];
    //         int power = blockAttributes[0]; //type?
    //         System.out.println(Arrays.toString(blockAttributes));
    //         Block block = BlockFactory.buildBlock(power, x, y, id);
    //         blockList.add(block);
    //             root.getChildren().add(block.getShape());
    //             x += BlockFactory.getWidth() + 5;
    //             block.createRectangleColor(row);
    //     }
    //     x = 3;
    //     y += BlockFactory.getHeight() + 5;
    // }

    /**
     * Hace visibles los bloques y, mediante una llamada al server,
     * determina y asigna los atributos de los bloques del siguiente nivel.
     * TODO: llamar al server y cambiar el poder de los bloques
     */
    private void resetBlocks(){
        // ArrayList<int[]> blockAttributesArray = client.get_blocks();
        for(Block block : blockList){
            block.getShape().setVisible(true);
            //CAMBIAR EL PODER
        }


    }

    private void buildBallList() {
        Ball ball = new Ball(STAGE_WIDTH - 100, STAGE_HEIGHT - 180, this);
        ballList.add(ball);
        for (Ball element : ballList){
            root.getChildren().add(element.getShape());
        }
    }

    public PlayerBar getPlayerBar() {
        return playerBar;
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    public void endGame(){

    }

    public GameWindow getGameWindow(){
        return this;
    }

    /**
     * Agrega una bola al juego
     */
    public void newBall() {
        boolean flag = false;
        for(Ball ball : ballList ){
            if (ball.getVisibility() == false){
                ball.recycle(this.STAGE_WIDTH/2, this.STAGE_HEIGHT/2);
                flag = true;
                break;
            }
        }
        if (!flag){
            int y = STAGE_HEIGHT/2;
            int x = STAGE_WIDTH/2;
            Ball ball = new Ball(x, y, this);
            ballList.add(ball);
            root.getChildren().add(ball.getShape());
            client.add_ball();
        }
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

    public void minusOneLife(){
        client.take_life();
        int life = client.get_lives();
        String vidas = Integer.toString(life);
        lives.setText(vidas);
    }

    /**
     * Aumenta la velocidad de las bolas
     */
    public void speedUpBalls(){
        Iterator<Ball> itr = ballList.iterator();
        while(itr.hasNext()){
            Ball ball = itr.next();
            ball.speedUp();
        }
        Ball ball = ballList.get(0);
        float xSpeed = ball.getXSpeed();
        float ySpeed = ball.getYSpeed();
        client.set_ball_speed_x(xSpeed);
        client.set_ball_speed_y(ySpeed);
    }

    public void updatePuntos(){
        int pts = client.get_score();
        String puntaje = Integer.toString(pts);
        puntos.setText(puntaje);
    }

    public void nextLevel(){
        client.level_up();
        int lvl = client.get_level();
        String niv = Integer.toString(lvl);
        level.setText(niv);
        setUpNextLevel();
    }

    /**
     * Dobla el tamaño de la barra del jugador
     */
    public void biggerPlayerbar(){
        playerBar.makeBigger();
        int barWidth = (int )playerBar.getShape().getWidth();
        client.set_paddle_width(barWidth);
    }

    /**
     * Reduce a la mitad el tamaño de la barra del jugador
     */
    public void smallerPlayerbar(){
        playerBar.makeSmaller();
        int barWidth = (int )playerBar.getShape().getWidth();
        client.set_paddle_width(barWidth);
    }
    public static void terminarJuego(char condicion){
        //Llamar ventana game over
        //GameStage.close();
        //stagePrincipal.show();
    }

    private void setUpNextLevel(){
        resetBlocks();
        for(Ball ball : ballList){
            ball.setInvisible();
        }
        newBall();
        speedUpBalls();
    }

    private void createLabels() {

        double fontSize = 15;
        FontWeight fontWeight = FontWeight.BOLD;
        Font numberFont = Font.font("Biome", fontWeight,fontSize);

        double labelFontSize = 10;
        FontWeight labelFontWeight = FontWeight.BOLD;
        Font labelFont = Font.font("Biome", labelFontWeight,labelFontSize);

        int pts = client.get_score();
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

        int lvl = client.get_level();
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

        int life = client.get_lives();
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

}
