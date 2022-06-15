#pragma once

#include <stdlib.h>
#include <stdbool.h>

#include "constants.h"

typedef struct
{
    int row;      // Fila en la que se encuentra el bloque
    int column;   // Columna en la que se encuentra el bloque
    int level;    // Nivel del bloque 1-4
    int power_up; // Identifica el tipo de poder que tiene el bloque
} Block;

typedef struct
{
    int id;     // Identificador de la bola
    int pos_x;  // Posición en el eje x de la bola
    int pos_y;  // Posición en el eje y de la bola
    int hidden; // Indica si la bola está oculta
} Ball;

typedef struct
{
    int width;    // Ancho del paddle
    int position; // Posición del paddle
} Paddle;

typedef struct
{
    int score; // Puntuación del jugador
    int lives; // Vidas del jugador
    int level; // Nivel del jugador

    int existing_balls; // Cantidad de bolas que tiene el jugador

    Block *blocks[BLOCK_ROWS][BLOCK_COLUMNS]; // Matriz de bloques
    Ball *balls[MAX_BALLS];                   // Array de bolas
    Paddle *paddle;                           // Paddle del jugador
} GameData;

/*
    Inicializa los datos del juego
        @returns Un puntero hacia el espacio en memoria de los datos del juego
*/
GameData *start_game();

/*
    Genera todos los bloques del juego y los coloca en la matriz de bloques
        @param game_data Puntero hacia los datos del juego
*/
void generate_blocks(GameData *game_data);

/*
    Indica en qué nivel se encuentra un bloque dependiendo de su fila
        @param row_index Fila del bloque
        @returns Nivel del bloque
*/
int get_block_level(int row_index);

/*
    Genera un número aleatorio entre 0 y 1 que la probabilidad de
    que un bloque tenga un poder. Si el número es mayor a 0.7, genera
    un número aleatorio entre un mínimo y un máximo que indica el poder del bloque.
        @param min_number: Número mínimo que puede tener un poder
        @param max_number: Número máximo que puede tener un poder
        @returns Número que representa el poder del bloque. -1 si no tiene poder
*/
int generate_random_powerup(const int min_number, const int max_number);

/*
    Crea una nueva bola y la coloca en el array de bolas.
    Si hay una bola oculta, la muestra.
        @param game_data: Puntero hacia los datos del juego
        @returns Puntero hacia la bola creada
*/
Ball *create_new_ball(GameData *game_data);

/*
    Busca una bola con un id dado dentro del array de bolas.
        @param game_data: Puntero hacia los datos del juego
        @param id: Identificador de la bola
        @returns Puntero hacia la bola encontrada
*/
Ball *get_ball_by_id(GameData *game_data, int ball_id);