package org.breakout;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class SpectatorSocket {
    private ServerSocket server_socket;
    private Socket socket_client;

    private InputStreamReader reader;

    private BufferedReader input_buffer;

    SpectatorSocket(int PORT) {
        try {
            this.server_socket = new ServerSocket(PORT);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void get_message() {
        try {
            this.socket_client = server_socket.accept();

            this.reader = new InputStreamReader(socket_client.getInputStream());

            this.input_buffer = new BufferedReader(reader);

            while (input_buffer.ready()) {
                String msg = input_buffer.readLine();

                System.out.println(msg);
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}