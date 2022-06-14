#include "server.h"

int server_socket;
// int client_socket;

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
    game_data = start_game();

    server_socket = stop_on_error(socket(AF_INET, SOCK_STREAM, 0));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(PORT);
    server_address.sin_addr.s_addr = INADDR_ANY;

    stop_on_error(bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)));
    stop_on_error(listen(server_socket, 2));

    pthread_t client_thread;
    pthread_create(&client_thread, NULL, test, NULL);

    pthread_t spectator_thread;
    pthread_create(&spectator_thread, NULL, test, NULL);

    pthread_join(client_thread, NULL);
    pthread_join(spectator_thread, NULL);

    close(server_socket);

    return 0;
}

void *test()
{
    int client_socket = stop_on_error(accept(server_socket, NULL, NULL));
    while (true)
    {
        receive_message(client_socket);
    }
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

void receive_message(const int client_socket)
{
    char received_message[INPUT_BUFFER_SIZE];
    stop_on_error(recv(client_socket, received_message, INPUT_BUFFER_SIZE, 0));

    if (strcmp(received_message, "0") == 0)
        send_blocks(client_socket);
    else if (strcmp(received_message, "1") == 0)
        send_balls(client_socket);
    else if (strcmp(received_message, "2") == 0)
        send_paddle(client_socket);
    else if (strcmp(received_message, "3") == 0)
        send_score(client_socket);
    else if (strcmp(received_message, "4") == 0)
        send_lives(client_socket);
    else if (strcmp(received_message, "5") == 0)
        send_level(client_socket);
    else if (strcmp(received_message, "6") == 0)
        add_life(client_socket);
    else if (strcmp(received_message, "7") == 0)
        take_life(client_socket);
    else if (strcmp(received_message, "8") == 0)
        level_up(client_socket);
    else if (strcmp(received_message, "9") == 0)
        add_ball(client_socket);
    else if (received_message[0] == '$')
        process_message(received_message, client_socket);
    else
        send_message("Mensaje no reconocido\n", client_socket);
}

void process_message(const char *received_message, const int client_socket)
{
    if (received_message[1] == '1')
    {
        int block_position[10];
        separate_parameters(received_message, block_position);
        destroy_block(block_position[0], block_position[1], client_socket);
    }
    else if (received_message[1] == '2')
    {
        int ball_info[2];
        separate_parameters(received_message, ball_info);
        move_ball_x(ball_info[0], ball_info[1], client_socket);
    }
    else if (received_message[1] == '3')
    {
        int ball_info[2];
        separate_parameters(received_message, ball_info);
        move_ball_y(ball_info[0], ball_info[1], client_socket);
    }
    else if (received_message[1] == '6')
    {
        int width[1];
        separate_parameters(received_message, width);
        set_paddle_width(width[0], client_socket);
    }
    else if (received_message[1] == '7')
    {
        int position[1];
        separate_parameters(received_message, position);
        set_paddle_position(position[0], client_socket);
    }
    else if (received_message[1] == '8')
    {
        int speed[1];
        separate_parameters(received_message, speed);
        set_paddle_speed(speed[0], client_socket);
    }
    else if (received_message[1] == '9')
    {
        int id[1];
        separate_parameters(received_message, id);
        hide_ball(id[0], client_socket);
    }
    else
        send_message("Mensaje no reconocido\n", client_socket);
}

void separate_parameters(const char *string, int *parameters)
{
    char parameter[5] = "\0";
    int parameter_index = 0;

    int string_size = strlen(string);
    char character[2] = "\0\0";
    for (int i = 3; i < string_size; i++)
    {
        character[0] = string[i];
        if (character[0] == ',')
        {
            parameters[parameter_index] = atoi(parameter);
            strcpy(parameter, "\0");
            parameter_index += 1;
        }
        else
        {
            strcat(parameter, &character[0]);
        }
    }
    parameters[parameter_index] = atoi(parameter);
}

void send_message(const char *message, const int client_socket)
{
    int message_size = strlen(message);
    stop_on_error(send(client_socket, message, message_size, 0));
}

void send_balls(const int client_socket)
{
    char ball_str[55];
    for (int i = 0; i < game_data->existing_balls; i++)
    {
        sprintf(ball_str, "%d,%d,%d\n",
                game_data->balls[i]->id,
                game_data->balls[i]->pos_x,
                game_data->balls[i]->pos_y);

        send_message(ball_str, client_socket);
    }
}

void send_blocks(const int client_socket)
{
    char block_str[12];
    for (int i = 0; i < BLOCK_ROWS; i++)
    {
        for (int j = 0; j < BLOCK_COLUMNS; j++)
        {
            sprintf(block_str, "%d,%d,%d,%d\n",
                    game_data->blocks[i][j]->row,
                    game_data->blocks[i][j]->column,
                    game_data->blocks[i][j]->level,
                    game_data->blocks[i][j]->power_up);

            send_message(block_str, client_socket);
        }
    }
}

void send_paddle(const int client_socket)
{
    char paddle_str[15];
    sprintf(paddle_str, "%d,%d,%d\n",
            game_data->paddle->width,
            game_data->paddle->position,
            game_data->paddle->speed);

    send_message(paddle_str, client_socket);
}

void send_score(const int client_socket)
{
    char score_str[10];
    sprintf(score_str, "%d\n", game_data->score);

    send_message(score_str, client_socket);
}

void send_lives(const int client_socket)
{
    char lives_str[10];
    sprintf(lives_str, "%d\n", game_data->lives);

    send_message(lives_str, client_socket);
}

void send_level(const int client_socket)
{
    char level_str[10];
    sprintf(level_str, "%d\n", game_data->level);

    send_message(level_str, client_socket);
}

void add_life(const int client_socket)
{
    game_data->lives += 1;

    send_lives(client_socket);
}

void take_life(const int client_socket)
{
    game_data->lives -= 1;

    send_lives(client_socket);
}

void level_up(const int client_socket)
{
    game_data->level += 1;

    send_level(client_socket);
}

void add_ball(const int client_socket)
{
    create_new_ball(game_data);

    send_balls(client_socket);
}

void move_ball_x(const int id, const int pos_x, const int client_socket)
{
    Ball *ball = get_ball_by_id(game_data, id);
    ball->pos_x = pos_x;

    char pos_x_str[6];
    sprintf(pos_x_str, "%d\n", ball->pos_x);

    send_message(pos_x_str, client_socket);
}

void move_ball_y(const int id, const int pos_y, const int client_socket)
{
    Ball *ball = get_ball_by_id(game_data, id);
    ball->pos_y = pos_y;

    char pos_y_str[6];
    sprintf(pos_y_str, "%d\n", ball->pos_y);

    send_message(pos_y_str, client_socket);
}

void set_paddle_width(const int width, const int client_socket)
{
    game_data->paddle->width = width;

    char width_str[6];
    sprintf(width_str, "%d\n", game_data->paddle->width);

    send_message(width_str, client_socket);
}

void set_paddle_position(const int position, const int client_socket)
{
    game_data->paddle->position = position;

    char position_str[6];
    sprintf(position_str, "%d\n", game_data->paddle->position);

    send_message(position_str, client_socket);
}

void set_paddle_speed(const int speed, const int client_socket)
{
    game_data->paddle->speed = speed;

    char speed_str[6];
    sprintf(speed_str, "%d\n", game_data->paddle->speed);

    send_message(speed_str, client_socket);
}

void destroy_block(const int row, const int column, const int client_socket)
{
    Block *block = game_data->blocks[row][column];
    game_data->score += block->level * game_data->level * BASE_POINTS;

    char position_str[10];
    sprintf(position_str, "%d,%d\n", block->row, block->column);

    free(block);

    send_message(position_str, client_socket);
}

void hide_ball(const int id, const int client_socket)
{
    Ball *ball = get_ball_by_id(game_data, id);
    ball->pos_x = INITIAL_POS_X;
    ball->pos_y = INITIAL_POS_Y;
    ball->hidden = true;

    char hidden_str[15];
    sprintf(hidden_str, "%d,%d,%d\n",
            ball->id,
            ball->pos_x,
            ball->pos_y);

    send_message(hidden_str, client_socket);
}