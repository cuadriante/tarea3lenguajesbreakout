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

void *set_sockets(int *thread_id);

void receive_message();
void send_message(const char *message, const int socket);

void process_message(const char *received_message);
void separate_parameters(const char *string, int *parameters);

void send_balls();
void send_blocks();
void send_paddle();

void send_score();
void send_lives();
void send_level();

void add_life();
void take_life();
void level_up();
void add_ball();

void move_ball_x(const int id, const int pos_x);
void move_ball_y(const int id, const int pos_y);

void set_paddle_width(const int width);
void set_paddle_position(const int position);

void destroy_block(const int pos_x, const int pos_y);
void hide_ball(const int id);