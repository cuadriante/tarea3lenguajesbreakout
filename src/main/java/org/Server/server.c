#include "server.h"
#pragma comment(lib,"ws2_32.lib")

int server_socket;
struct sockaddr_in server_address;

int main()
{
    //Varas de los sockets de Windows
    WSADATA wsa;

    printf("\nInitialising Winsock...\n");
    if (WSAStartup(MAKEWORD(2,2),&wsa) != 0)
    {
        printf("Failed. Error Code : %d",WSAGetLastError());
        return 1;
    }

    printf("Initialised.\n");

    GameData *game_data = start_game();

    server_socket = stop_on_error(socket(AF_INET, SOCK_STREAM, 0));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(PORT);
    server_address.sin_addr.s_addr = INADDR_ANY;

    stop_on_error(bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)));

    stop_on_error(listen(server_socket, 2));

    const int client_socket = stop_on_error(accept(server_socket, NULL, NULL));

    while (true)
    {
        receive_message(client_socket, game_data);
    }

    close(server_socket);

    return 0;
}

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

void receive_message(const int client_socket, GameData *game_data)
{
    char received_message[INPUT_BUFFER_SIZE];
    stop_on_error(recv(client_socket, received_message, INPUT_BUFFER_SIZE, 0));

    if (strcmp(received_message, "1") == 0)
    {
        send_blocks(client_socket, game_data);
    }
    else
    {
        send_message(client_socket, "Mensaje no reconocido\n");
    }
}

void send_message(const int client_socket, const char *message)
{
    int message_size = strlen(message);
    stop_on_error(send(client_socket, message, message_size, 0));
}

void send_blocks(const int client_socket, GameData *game_data)
{
    char block_str[11];
    for (int i = 0; i < BLOCK_ROWS; i++)
    {
        for (int j = 0; j < BLOCK_COLUMNS; j++)
        {
            Block *block = game_data->blocks[i][j];

            sprintf(block_str, "%d,%d,%d,%d,%d\n",
                    block->broken,
                    block->row,
                    block->column,
                    block->level,
                    block->power_up);

            send_message(client_socket, block_str);
        }
    }
}