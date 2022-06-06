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
    public static int pts = 0;
    public static int lvl = 0;

    public static int life = 3;

    private static final Text puntosLabel = new Text();
    private static final Text levelLabel = new Text();

    private static final Text livesLabel = new Text();
    private static final Text puntos = new Text();
    private static final Text level = new Text();

    private static final Text lives = new Text();
    private final PlayerBar playerBar = new PlayerBar(200, 350, BlockFactory.getWidth(), BlockFactory.getHeight());
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    private ArrayList<Block> blockList = new ArrayList<Block>();
    final GameLoop GAME_LOOP = new GameLoop(this, ballList, blockList, playerBar); // creo que esto es un singleton xd // no es


    GameWindow(Stage Lobby) throws Exception {
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
    // public void buildBlockList2(){
    //     int matriz[][]; //DECLARA MATRIZ
    //     int x = 3;
    //     int y = 40;
    //     int id = 0;

    //     for (int i = 0; i < matriz.length; i++){
    //         for (int j = 0; i < matriz[0].length; j++){
    //             int type = matriz[i][j];
    //             Block block = BlockFactory.buildBlock(type, x, y, id);
    //             blockList.add(block);
    //             root.getChildren().add(block.getShape());
    //             x += BlockFactory.getWidth() + 5;
    //         }
    //         x = 3;
    //         y += BlockFactory.getHeight() + 5;
    //     }
    // }

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

    public void removeBall(Ball ball){
        root.getChildren().remove(ball.getShape());
        ballList.remove(ball);
        System.out.println(ballList.size());
    }

    /**
     * Agrega una bola al juego
     */
    public void newBall() {
        int y = STAGE_HEIGHT/2;
        int x = STAGE_WIDTH/2;
        Ball newBall = new Ball(x, y, this);
        ballList.add(newBall);
        root.getChildren().add(newBall.getShape());
    }

    /**
     * Agrega una vida al jugadoor
     */
    public void newLife() {
        life++;
        String vidas = Integer.toString(life);
        lives.setText(vidas);
    }

    /**
     * Aumenta la velocidad de las bolas
     */
    private void speedUpBalls(){
        Iterator<Ball> itr = ballList.iterator();
        while(itr.hasNext()){
            Ball ball = itr.next();
            ball.speedUp();
        }
    }

    public static void updatePuntos(int suma){
        pts = pts+suma;
        String puntaje = Integer.toString(pts);
        puntos.setText(puntaje);
    }

    //*Le borré el parametro de entrada porque 
    //* no entendí para que se usaba
    public void nextLevel(){
        lvl++;
        String niv = Integer.toString(lvl);
        puntos.setText(niv);
        // setUpNextLevel();
        // speedUpBalls();
    }
    public static void terminarJuego(char condicion){
        //Llamar ventana game over
        //GameStage.close();
        //stagePrincipal.show();
    }

    // private void setUpNextLevel(){
        
    // }



    private void createLabels() {

        double fontSize = 15;
        FontWeight fontWeight = FontWeight.BOLD;
        Font numberFont = Font.font("Biome", fontWeight,fontSize);

        double labelFontSize = 10;
        FontWeight labelFontWeight = FontWeight.BOLD;
        Font labelFont = Font.font("Biome", labelFontWeight,labelFontSize);

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
