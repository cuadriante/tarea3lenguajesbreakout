#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>

#include <sys/socket.h>
#include <sys/types.h>

#include <netinet/in.h>

const int PORT = 8080;
const int BUFFER_SIZE = 256;

int server_socket;
struct sockaddr_in server_address;

int stop_on_error(const int returned_value)
{
    if (returned_value < 0)
    {
        close(server_socket);
        perror("Error ");
        exit(EXIT_FAILURE);
    }
    else
    {
        return returned_value;
    }
}

void send_message(const int client_socket, const char *message)
{
    stop_on_error(send(client_socket, message, BUFFER_SIZE, 0));
}

void receive_message()
{
    const int client_socket = stop_on_error(accept(server_socket, NULL, NULL));

    const char received_message[BUFFER_SIZE];
    stop_on_error(recv(client_socket, &received_message, BUFFER_SIZE, 0));
    printf("Mensaje del cliente: %s\n", &received_message);

    send_message(client_socket, "Hola cliente");
}

int main()
{
    server_socket = stop_on_error(socket(AF_INET, SOCK_STREAM, 0));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(PORT);
    server_address.sin_addr.s_addr = INADDR_ANY;

    stop_on_error(bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)));

    stop_on_error(listen(server_socket, 2));

    while (true)
    {
        receive_message();
    }

    close(server_socket);

    return 0;
}