#pragma once

#include <stdio.h>
#include <string.h>
#include <errno.h>

#include <unistd.h>
//#include <sys/socket.h>
#include <sys/types.h>

//#include <netinet/in.h>

#include <winsock2.h> //Para Windows
//#include <winsock.h>


#include "constants.h"
#include "GameData.h"

int stop_on_error(const int returned_value);

void receive_message(const int client_socket, GameData *game_data);

void send_message(const int client_socket, const char *message);
void send_blocks(const int client_socket, GameData *game_data);