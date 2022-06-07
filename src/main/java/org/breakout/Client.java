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

            String response = input_buffer.readLine();

            System.out.println(response);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_blocks() {
        try {
            output_buffer.write("1\0");
            output_buffer.flush();

            while (input_buffer.ready()) {
                String block_str = input_buffer.readLine();
                System.out.println(block_str);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}