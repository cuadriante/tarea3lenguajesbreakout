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

/*
    Verifica si una función retorna un valor de error. En dado
    caso de que haya un error, imprime un mensaje de error y
    termina la ejecución del programa. En caso contrario, no hace nada
        @param returned_value: El valor que retorna la función
        @returns El mismo valor dado por el parámetro
*/
int stop_on_error(const int returned_value);

/*
    Espera a que un cliente se conecte al servidor y guarda
    su identificador.
        @param thread_id: El identificador del hilo que se está ejecutando
*/
void *set_sockets(int *thread_id);

/*
    Recibes mensajes del cliente y responde de acuerdo a
    lo solicitado. Procesa mensajes con un único parámetro.
*/
void receive_message();

/*
    Envía un mensaje a un cliente.
        @param message: El mensaje a enviar
        @param socket: El identificador del cliente
*/
void send_message(const char *message, const int socket);

/*
    Procesa un mensaje con más de un parámetro y responde
    de acuerdo a lo solicitado.
        @param received_message: El mensaje a procesar
*/
void process_message(const char *received_message);

/*
    Toma un string con números separados por comas, los
    separa y convierte en ints.
        @param string: El string a separar
        @param numbers: El arreglo donde se guardarán los números
*/
void separate_parameters(const char *string, int *parameters);

/*
    Convierte los atributos de una estructura de tipo Block
    en un string separado por comas y lo envía al cliente.
*/
void send_blocks();

/*
    Convierte los atributos de una estructura de tipo PAddle
    en un string separado por comas y lo envía al cliente.
*/
void send_paddle();

/*
    Envía un string con la puntuación actual al cliente.
*/
void send_score();

/*
    Envía un string con el número de vidas actual al cliente.
*/
void send_lives();

/*
    Envía un string con el nivel actual al cliente.
*/
void send_level();

/*
    Añade una vida al jugador y envía las vidas actuales
    al cliente y el espectador.
*/
void add_life();

/*
    Resta una vida al jugador y envía las vidas actuales
    al cliente y el espectador.
*/
void take_life();

/*
    Añade un nivel al jugador y envía el nivel actual
    al cliente y el espectador.
*/
void level_up();

/*
    Crea una bola nueva o reutiliza una ya creada y
    envía su identificador al cliente y el espectador.
*/
void add_ball();

/*
    Envía el id de una bola al cliente y el espectador.
        @param ball_id: El identificador de la bola
*/
void send_ball(const int ball_id);

/*
    Cambia la posición de una bola en el eje x
    y envía la posición actual al cliente y el espectador.
        @param id: El identificador de la bola
        @param pos_x: La nueva posición en el eje x
*/
void move_ball_x(const int id, const int pos_x);

/*
    Cambia la posición de una bola en el eje y
    y envía la posición actual al cliente y el espectador.
        @param id: El identificador de la bola
        @param pos_x: La nueva posición en el eje y
*/
void move_ball_y(const int id, const int pos_y);

/*
    Cambia el ancho del paddle y envía el nuevo valor
    al cliente y el espectador.
        @param width: El nuevo ancho del paddle
*/
void set_paddle_width(const int width);

/*
    Cambia la posición del paddle y envía la nueva
    posición al cliente y el espectador.
        @param position: La nueva posición del paddle
*/
void set_paddle_position(const int position);

/*
    Libera la memoria asignada a una estructura de tipo Block
    y envía su posición al cliente y el espectador.
        @param pos_x: La columna del bloque a eliminar
        @param pos_y: La fila del bloque a eliminar
*/
void destroy_block(const int pos_x, const int pos_y);

/*
    Esconde una bola para reutilizar la memoria asignada y
    envía su posición al cliente y el espectador.
        @param id: El identificador de la bola
*/
void hide_ball(const int id);