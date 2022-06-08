package org.breakout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

public class Client {
    private Socket socket;
    private InputStreamReader reader;
    private OutputStreamWriter writer;

    private BufferedReader input_buffer;
    private BufferedWriter output_buffer;

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
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_blocks() {
        try {
            send_message("1\0");

            while (input_buffer.ready()) {
                String block_str = input_buffer.readLine();
                System.out.println(block_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_balls() {
        try {
            send_message("2\0");

            while (input_buffer.ready()) {
                String ball_str = input_buffer.readLine();
                System.out.println(ball_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_score() {
        try {
            send_message("3\0");

            while (input_buffer.ready()) {
                String score_str = input_buffer.readLine();
                System.out.println(score_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_lives() {
        try {
            send_message("4\0");

            while (input_buffer.ready()) {
                String lives_str = input_buffer.readLine();
                System.out.println(lives_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_level() {
        try {
            send_message("5\0");

            while (input_buffer.ready()) {
                String level_str = input_buffer.readLine();
                System.out.println(level_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void test_communication() {
        try {
            get_blocks();
            get_balls();
            get_score();
            get_lives();
            get_level();
            send_message("ola");
            System.out.println(input_buffer.readLine());
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}