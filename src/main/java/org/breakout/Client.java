package org.breakout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private InputStreamReader reader;
    private OutputStreamWriter writer;

    private BufferedReader input_buffer;
    private BufferedWriter output_buffer;

    private Adapter adapter = new Adapter();

    public Client(int PORT) {
        try {
            this.socket = new Socket("localhost", 8080);

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
                System.out.println("Vidas: " + lives_str);
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

    public void add_life() {
        try {
            send_message("6\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                System.out.println("Ahora tiene " + lives_str + " vidas");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void take_life() {
        try {
            send_message("7\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                System.out.println("Ahora tiene " + lives_str + " vidas");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void level_up() {
        try {
            send_message("8\0");

            while (input_buffer.ready()) {
                String level_str = input_buffer.readLine();
                System.out.println("Subió al nivel: " + level_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void add_ball() {
        try {
            send_message("9\0");

            while (input_buffer.ready()) {
                String ball_str = input_buffer.readLine();
                System.out.println("Bola: " + ball_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void move_ball_x(int ball_id) {
        try {
            send_message("$2,"
                    + Integer.toString(ball_id) + "\0");

            while (input_buffer.ready()) {
                String pos_x_str = input_buffer.readLine();
                System.out.println("Posición X de la bola " +
                        Integer.toString(ball_id) + ": " + pos_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void move_ball_y(int ball_id) {
        try {
            send_message("$3,"
                    + Integer.toString(ball_id) + "\0");

            while (input_buffer.ready()) {
                String pos_x_str = input_buffer.readLine();
                System.out.println("Posición Y de la bola " +
                        Integer.toString(ball_id) + ": " + pos_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void set_ball_speed_x(int speed) {
        // !!!!!
        // La velocidad de las bolas ahora es global.
        // Está en GameData y no en Ball.

        try {
            send_message("$4," + Integer.toString(speed) + "\0");

            while (input_buffer.ready()) {
                String speed_x_str = input_buffer.readLine();
                System.out.println("Velocidad X de las bolas: " + speed_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void set_ball_speed_y(int speed) {
        try {
            send_message("$5," + Integer.toString(speed) + "\0");

            while (input_buffer.ready()) {
                String speed_x_str = input_buffer.readLine();
                System.out.println("Velocidad Y de las bolas: " + speed_x_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void set_paddle_width(int width) {
        try {
            send_message("$6," + Integer.toString(width) + "\0");

            while (input_buffer.ready()) {
                String width_str = input_buffer.readLine();
                System.out.println("Ancho del paddle: " + width_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void set_paddle_position(int position) {
        try {
            send_message("$7," + Integer.toString(position) + "\0");

            while (input_buffer.ready()) {
                String position_str = input_buffer.readLine();
                System.out.println("Posición del paddle: " + position_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void set_paddle_speed(int speed) {
        try {
            send_message("$8," + Integer.toString(speed) + "\0");

            while (input_buffer.ready()) {
                String speed_str = input_buffer.readLine();
                System.out.println("Velocidad del paddle: " + speed_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
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

    public void test_communication() {
        try {
            // get_blocks();
            // get_balls();
            // get_score();
            // get_lives();
            // get_level();
            // add_life();
            // add_life();
            // take_life();
            // level_up();
            // add_ball();
            // send_message("ola\0");
            // System.out.println("Error: " + input_buffer.readLine());
            // destroy_block(7, 5);
            // destroy_block(1, 7);
            // destroy_block(2, 2);
            // get_score();
            set_ball_speed_x(8);
            set_ball_speed_y(12);
            get_balls();
            System.out.println("----------------");
            add_ball();
            set_ball_speed_x(-6);
            set_ball_speed_x(-20);
            set_ball_speed_y(-2);
            get_balls();
            System.out.println("----------------");
            get_paddle();
            set_paddle_width(55);
            set_paddle_position(205);
            set_paddle_position(150);
            set_paddle_speed(10);
            set_paddle_speed(-10);
            get_paddle();

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}