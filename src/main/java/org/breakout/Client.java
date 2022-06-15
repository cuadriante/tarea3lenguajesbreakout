package org.breakout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Jugador cliente
 */

public class Client {
    private Socket socket;
    private final static int CLIENT_PORT = 8080;
    private InputStreamReader reader;
    private OutputStreamWriter writer;

    private BufferedReader input_buffer;
    private BufferedWriter output_buffer;

    private Adapter adapter = new Adapter();
    private static Client instance = null;

    /**
     * singleton
     */
    public static Client getInstance() {
        if (instance == null) {
            instance = new Client(CLIENT_PORT);
        }
        return instance;
    }

    /**
     * Constructor del cliente, recibe el puerto al que conectarse
     * 
     * @param PORT puerto al que conectarse
     */
    private Client(int PORT) {
        try {
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
     * Recibe del server la lista de bloques que desplegar en la interfaz
     * 
     * @return lista de bloques
     */
    public ArrayList<int[]> get_blocks() {
        ArrayList<int[]> blockAttributesArray = new ArrayList<>();
        try {
            send_message("0\0");
            while (input_buffer.ready()) {
                String block_str = input_buffer.readLine();
                // System.out.println(block_str);
                int blockAttributes[] = adapter.stringToBlockAttributes(block_str);
                blockAttributesArray.add(blockAttributes);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return blockAttributesArray;
    }

    /**
     * Recibe del server la posicion de la barra del jugador
     */
    public void get_paddle() {
        try {
            send_message("2\0");

            while (input_buffer.ready()) {
                String paddle_str = input_buffer.readLine();
                // System.out.println("Paddle: " + paddle_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del server la puntuacion actual del juego
     * 
     * @return puntuacion actual
     */
    public int get_score() {
        int returned = -1;
        try {
            send_message("3\0");

            while (input_buffer.ready()) {
                String score_str = input_buffer.readLine();
                // System.out.println("Puntuación: " + score_str);
                returned = adapter.singleDatatoInt(score_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return returned;
    }

    /**
     * Recibe del server las vidas actuales disponibles
     * 
     * @return vidas actuales
     */
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

    /**
     * Recibe del serber el nivel actual de juego
     * 
     * @return nivel actual
     */
    public int get_level() {
        int returned = -1;
        try {
            send_message("5\0");

            while (input_buffer.ready()) {
                String level_str = input_buffer.readLine();
                returned = adapter.singleDatatoInt(level_str);
                // System.out.println("Nivel: " + level_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return returned;
    }

    /**
     * Recibe del servidor la instruccion de agregar una vida al juego
     */
    public void add_life() {
        try {
            send_message("6\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                // System.out.println("Ahora tiene " + lives_str + " vidas");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor la instruccion de eliminar una vida del juego
     */
    public void take_life() {
        try {
            send_message("7\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                // System.out.println("Ahora tiene " + lives_str + " vidas");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor la instruccion de cambiar de nivel
     */
    public void level_up() {
        try {
            send_message("8\0");

            while (input_buffer.ready()) {
                String level_str = input_buffer.readLine();
                // System.out.println("Subió al nivel: " + level_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor la instruccion de agregar una bola al juego
     */
    public void add_ball() {
        try {
            send_message("9\0");

            while (input_buffer.ready()) {
                String ball_str = input_buffer.readLine();
                // System.out.println("Bola: " + ball_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor una nueva posicion para una bola segun su id
     * 
     * @param ball_id id de la bola
     * @param pos_x   posicion en x de la bola
     */
    public void setPosX(int ball_id, int pos_x) {
        try {
            send_message("$2,"
                    + Integer.toString(ball_id) + ","
                    + Integer.toString(pos_x) + "\0");

            while (input_buffer.ready()) {
                String pos_x_str = input_buffer.readLine();
                // System.out.println("Posición X de la bola " +
                // Integer.toString(ball_id) + ": " + pos_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor una nueva posicion para una bola segun su id
     * 
     * @param ball_id id de la bola
     * @param pos_y   posicion en y de la bola
     */
    public void setPosY(int ball_id, int pos_y) {
        try {
            send_message("$3,"
                    + Integer.toString(ball_id) + ","
                    + Integer.toString(pos_y) + "\0");

            while (input_buffer.ready()) {
                String pos_x_str = input_buffer.readLine();
                // System.out.println("Posición Y de la bola " +
                // Integer.toString(ball_id) + ": " + pos_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Cambia el ancho de la barra de jugador segun lo establecido
     * 
     * @param width ancho de la barra de jugador
     */
    public void set_paddle_width(int width) {
        try {
            send_message("$4," + Integer.toString(width) + "\0");

            while (input_buffer.ready()) {
                String width_str = input_buffer.readLine();
                System.out.println("Ancho del paddle: " + width_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Cambia la posicion de la barra del jugador a la especificada
     * 
     * @param position nueva posicion de la barra de jugador
     */
    public void set_paddle_position(int position) {
        try {
            send_message("$5," + Integer.toString(position) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                // System.out.println("Posición del paddle: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Envia al servidor el bloque a eliminar
     * 
     * @param row    fila del bloque a eliminar
     * @param column columna del bloque a eliminar
     */
    public void destroy_block(int row, int column) {
        try {
            send_message("$1,"
                    + Integer.toString(row) + ","
                    + Integer.toString(column) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                // System.out.println("Posición del bloque eliminado: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Recibe del servidor una bola segun su id
     * 
     * @param ball_id id de la bola
     */
    public void get_ball(int ball_id) {
        try {
            send_message("$7," + Integer.toString(ball_id) + "\0");

            while (input_buffer.ready()) {
                String ball_str = input_buffer.readLine();
                // System.out.println("Bola: " + ball_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Envia al servidor la bola que debe de esconder segun su id
     * 
     * @param id id de la bola
     */
    public void hide_ball(int id) {
        try {
            send_message("$6,"
                    + Integer.toString(id) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                // System.out.println("Bola escondida: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Prueba la comunicacion entre el servidor y el cliente
     */
    public void test_communication() {
        try {
            get_blocks();
            // int i = 0;
            // while (true) {
            // // get_score();
            // // add_life();
            // // take_life();
            // // level_up();
            // // setPosX(1, i);
            // // setPosY(1, i);
            // // // get_ball(1);
            // // // get_ball(2);
            // // add_ball();
            // // hide_ball(1);
            // // destroy_block(7, i);
            // // set_paddle_position(i);
            // set_paddle_width(i);
            // i += 1;
            // Thread.sleep(3000);
            // }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}