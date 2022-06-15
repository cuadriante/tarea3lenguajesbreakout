package org.breakout.spectator;

import org.breakout.Adapter;
import org.breakout.blockFactory.Block;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class SpectatorClient extends Thread {
    private Socket socket;
    private InputStreamReader reader;
    private OutputStreamWriter writer;

    private BufferedReader input_buffer;
    private BufferedWriter output_buffer;

    private Adapter adapter = new Adapter();
    private SpectatorWindow spectatorWindow;

    /**
     * Constructor del cliente espectador, recibe el puerto al que conectarse
     * 
     * @param PORT puerto al que conectarse
     */
    public SpectatorClient(int PORT, SpectatorWindow sw) {
        try {
            this.spectatorWindow = sw;

            this.socket = new Socket("localhost", PORT);

            this.reader = new InputStreamReader(socket.getInputStream());
            this.writer = new OutputStreamWriter(socket.getOutputStream());

            this.input_buffer = new BufferedReader(reader);
            this.output_buffer = new BufferedWriter(writer);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Envia un mensaje al server
     * 
     * @param message mensaje a enviar
     */
    public void send_message(String message) {
        try {
            output_buffer.write(message);
            output_buffer.flush();

            while (!input_buffer.ready())
                Thread.sleep(10);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe desde el server un mensaje con los
     * bloques que se deben dibujar en la pantalla
     * 
     * @return el mensaje recibido del server
     */
    public ArrayList<int[]> get_blocks() {
        ArrayList<int[]> blockAttributesArray = new ArrayList<>();
        try {
            send_message("0\0");
            while (input_buffer.ready()) {
                String block_str = input_buffer.readLine();
                System.out.println(block_str);
                int[] blockAttributes = adapter.stringToBlockAttributes(block_str);
                blockAttributesArray.add(blockAttributes);
            }
            spectatorWindow.buildBlockList(blockAttributesArray);
        } catch (Exception error) {
            error.printStackTrace();
        }
        return blockAttributesArray;
    }

    /**
     * Se encarga de recibir los mensajes del servidor
     */
    public void run() {
        try {
            get_blocks();
            while (true) {
                if (input_buffer.ready()) {
                    String message = input_buffer.readLine();
                    // System.out.println(message);
                    int id = adapter.processId(message);
                    String data = adapter.processData(message);
                    processMessage(id, data);
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Procesa los mensajes recibidos del servidor y los aplica
     * al juego
     * 
     * @param id   id del mensaje
     * @param data datos
     */
    private void processMessage(int id, String data) {
        switch (id) {
            case (0) -> { // PUNTAJE
                System.out.println("CambiarPuntaje");
                spectatorWindow.updatePuntos(data);
            }
            case (1) -> { // VIDAS
                System.out.println("CambiarVidas");
                int lives = adapter.singleDatatoInt(data);
                if (lives == 0) {
                    System.exit(0);
                } else {
                    spectatorWindow.newLife(data);
                }
            }
            case (2) -> { // NIVEL
                System.out.println("CambiarNivel");
                spectatorWindow.nextLevel(data);
            }
            case (3) -> {
                int dataArray[] = adapter.splitData(data);
                int ballId = dataArray[0];
                spectatorWindow.addBall(ballId);
            }
            case (4) -> {
                int dataArray[] = adapter.splitData(data);
                int ballId = dataArray[0];
                int posX = dataArray[1];
                spectatorWindow.setBallPosX(ballId, posX);
            }
            case (5) -> {
                int dataArray[] = adapter.splitData(data);
                int ballId = dataArray[0];
                int posY = dataArray[1];
                spectatorWindow.setBallPosY(ballId, posY);
            }
            case (6) -> {
                int width = adapter.singleDatatoInt(data);
                spectatorWindow.setPlayerBarWidth(width);
            }
            case (7) -> {
                int xPos = adapter.singleDatatoInt(data);
                spectatorWindow.setPlayerBarPos(xPos);
            }
            case (8) -> {
                int dataArray[] = adapter.splitData(data);
                Block block = spectatorWindow.getBlock(dataArray[0], dataArray[1]);
                spectatorWindow.breakBlock(block);
            }
            case (9) -> {
                int ballId = adapter.singleDatatoInt(data);
                spectatorWindow.hideBall(ballId);
            }

            default -> {
                // System.out.println("Mensaje no procesado");
            }
        }
    }
}