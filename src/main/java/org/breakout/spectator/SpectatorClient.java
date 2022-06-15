package org.breakout.spectator;

import org.breakout.Adapter;

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

public class SpectatorClient extends Thread{
    private Socket socket;
    private InputStreamReader reader;
    private OutputStreamWriter writer;

    private BufferedReader input_buffer;
    private BufferedWriter output_buffer;

    private Adapter adapter = new Adapter();
    private SpectatorWindow spectatorWindow;

    public SpectatorClient(int PORT, SpectatorWindow sw){
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

    public ArrayList<int[]> get_blocks() {
        ArrayList<int[]> blockAttributesArray = new ArrayList<>();
        try {
            send_message("0\0");
            while (input_buffer.ready()) {
                String block_str = input_buffer.readLine();
                System.out.println(block_str);
                int blockAttributes[] = adapter.stringToBlockAttributes(block_str);
                blockAttributesArray.add(blockAttributes);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return blockAttributesArray;
    }

    public void get_balls() {
        try {
            send_message("1\0");

            while (input_buffer.ready()) {
                String ball_str = input_buffer.readLine();
                System.out.println("Bola: " + ball_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * da la barra de juego
     */
    public void get_paddle() {
        try {
            send_message("2\0");

            while (input_buffer.ready()) {
                String paddle_str = input_buffer.readLine();
                System.out.println("Paddle: " + paddle_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public int get_score() {
        int returned = -1;
        try {
            send_message("3\0");

            while (input_buffer.ready()) {
                String score_str = input_buffer.readLine();
                System.out.println("Puntuación: " + score_str);
                returned = adapter.singleDatatoInt(score_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return returned;
    }

    public int get_lives() {
        int returned = -1;
        try {
            send_message("4\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                returned = adapter.singleDatatoInt(lives_str);
                // System.out.println("Vidas: " + lives_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return returned;
    }

    public int get_level() {
        int returned = -1;
        try {
            send_message("5\0");

            while (input_buffer.ready()) {
                String level_str = input_buffer.readLine();
                returned = adapter.singleDatatoInt(level_str);
                System.out.println("Nivel: " + level_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return returned;
    }

    public void destroy_block(int row, int column) {
        try {
            send_message("$1,"
                    + Integer.toString(row) + ","
                    + Integer.toString(column) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                System.out.println("Posición del bloque eliminado: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void hide_ball(int id) {
        try {
            send_message("$9,"
                    + Integer.toString(id) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                System.out.println("Bola escondida: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void run() {
        try {
            get_blocks();
            while(true){
                if (input_buffer.ready()) {
                    String message = input_buffer.readLine();
                    System.out.println(message);
                    int id = adapter.processId(message);
                    String data = adapter.processData(message);
                    processMesage(id, data);
                }
            }
        }catch (Exception error) {
            error.printStackTrace();
        }
    }
    

    private void processMesage(int id, String data) {
        switch(id){
            case(7)->{
                System.out.println("MoverBola");
                int xPos = adapter.singleDatatoInt(data);
                spectatorWindow.setPlayerBarPos(xPos);
            }
            default->{
                System.out.println("Mensaje no procesado");
            }
        }
    }
}