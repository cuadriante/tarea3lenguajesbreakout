#pragma once
// #pragma comment(lib, "ws2_32.lib")

#include <stdio.h>
#include <string.h>
#include <errno.h>

#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>

#include <netinet/in.h>

// #include <winsock2.h> //Para Windows

#include "constants.h"
#include "GameData.h"

int stop_on_error(const int returned_value);

void receive_message();
void process_message(const char *received_message);

void send_message(const char *message);
void send_blocks();
void send_score();
void send_lives();
void send_level();