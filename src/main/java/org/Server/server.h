#pragma once
#pragma comment(lib, "ws2_32.lib")

#include <stdio.h>
#include <string.h>
#include <errno.h>

#include <unistd.h>
// #include <sys/socket.h>
#include <sys/types.h>

// #include <netinet/in.h>

#include <winsock2.h> //Para Windows

#include "constants.h"
#include "GameData.h"

int stop_on_error(const int returned_value);

void receive_message();
void send_message(const char *message);

void process_message(const char *received_message);
void separate_parameters(const char *string, int *parameters);

void send_blocks();
void send_balls();

void send_score();
void send_lives();
void send_level();

void add_life();
void take_life();
void level_up();
void add_ball();
void destroy_block(const int pos_x, const int pos_y);