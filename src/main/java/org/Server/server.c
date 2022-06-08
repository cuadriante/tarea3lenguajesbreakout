#include "server.h"

int server_socket;
int client_socket;

struct sockaddr_in server_address;

GameData *game_data;

int main()
{
    // Varas de los sockets de Windows
    // WSADATA wsa;
    // if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0)
    // {
    //     return 1;
    // }
    // game_data =
    game_data = start_game();

    server_socket = stop_on_error(socket(AF_INET, SOCK_STREAM, 0));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(PORT);
    server_address.sin_addr.s_addr = INADDR_ANY;

    stop_on_error(bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)));

    stop_on_error(listen(server_socket, 2));

    client_socket = stop_on_error(accept(server_socket, NULL, NULL));

    while (true)
    {
        receive_message();
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

void receive_message()
{
    char received_message[INPUT_BUFFER_SIZE];
    stop_on_error(recv(client_socket, received_message, INPUT_BUFFER_SIZE, 0));

    process_message(received_message);
}

void process_message(const char *received_message)
{
    if (strcmp(received_message, "1") == 0)
        send_blocks();
    else if (strcmp(received_message, "2") == 0)
        send_balls();
    else if (strcmp(received_message, "3") == 0)
        send_score();
    else if (strcmp(received_message, "4") == 0)
        send_lives();
    else if (strcmp(received_message, "5") == 0)
        send_level();
    else
    {
        send_message("Mensaje no reconocido\n");
    }
}

void send_message(const char *message)
{
    int message_size = strlen(message);
    stop_on_error(send(client_socket, message, message_size, 0));
}

void send_blocks()
{
    char block_str[11];
    for (int i = 0; i < BLOCK_ROWS; i++)
    {
        for (int j = 0; j < BLOCK_COLUMNS; j++)
        {
            sprintf(block_str, "%d,%d,%d,%d,%d\n",
                    game_data->blocks[i][j]->broken,
                    game_data->blocks[i][j]->row,
                    game_data->blocks[i][j]->column,
                    game_data->blocks[i][j]->level,
                    game_data->blocks[i][j]->power_up);

            send_message(block_str);
        }
    }
}

void send_balls()
{
    char ball_str[55];
    for (int i = 0; i < game_data->existing_balls; i++)
    {
        sprintf(ball_str, "%d,%d,%d,%d,%d\n",
                game_data->balls[i]->id,
                game_data->balls[i]->pos_x,
                game_data->balls[i]->pos_y,
                game_data->balls[i]->speed_x,
                game_data->balls[i]->speed_y);

        send_message(ball_str);
    }
}

void send_score()
{
    char score_str[10];
    sprintf(score_str, "%d\n", game_data->score);

    send_message(score_str);
}

void send_lives()
{
    char lives_str[10];
    sprintf(lives_str, "%d\n", game_data->lives);

    send_message(lives_str);
}

void send_level()
{
    char level_str[10];
    sprintf(level_str, "%d\n", game_data->level);

    send_message(level_str);
}