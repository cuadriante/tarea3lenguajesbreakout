#pragma once

#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <pthread.h>

#include <unistd.h>
#include <sys/types.h>

// #include <sys/socket.h>
// #include <netinet/in.h>

#include <winsock2.h> //Para Windows
#pragma comment(lib, "ws2_32.lib")

#include "constants.h"
#include "GameData.h"

int stop_on_error(const int returned_value);

void *test();

void receive_message(const int client_socket);
void send_message(const char *message, const int client_socket);

void process_message(const char *received_message, const int client_socket);
void separate_parameters(const char *string, int *parameters);

void send_balls(const int client_socket);
void send_blocks(const int client_socket);
void send_paddle(const int client_socket);

void send_score(const int client_socket);
void send_lives(const int client_socket);
void send_level(const int client_socket);

void add_life(const int client_socket);
void take_life(const int client_socket);
void level_up(const int client_socket);
void add_ball(const int client_socket);

void move_ball_x(const int id, const int pos_x, const int client_socket);
void move_ball_y(const int id, const int pos_y, const int client_socket);

void set_paddle_width(const int width, const int client_socket);
void set_paddle_position(const int position, const int client_socket);
void set_paddle_speed(const int speed, const int client_socket);

void destroy_block(const int pos_x, const int pos_y, const int client_socket);
void hide_ball(const int id, const int client_socket);