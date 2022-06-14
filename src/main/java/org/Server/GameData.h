#pragma once

#include <stdlib.h>
#include <stdbool.h>

#include "constants.h"

typedef struct
{
    int row;
    int column;
    int level;
    int power_up;
} Block;

typedef struct
{
    int id;
    int pos_x;
    int pos_y;
    int hidden;
} Ball;

typedef struct
{
    int width;
    int position;
} Paddle;

typedef struct
{
    int score;
    int lives;
    int level;

    int existing_balls;

    Block *blocks[BLOCK_ROWS][BLOCK_COLUMNS];
    Ball *balls[MAX_BALLS];
    Paddle *paddle;
} GameData;

GameData *start_game();

void generate_blocks(GameData *game_data);
int get_block_level(int row_index);
int generate_random_powerup(const int min_number, const int max_number);

void create_new_ball(GameData *game_data);

Ball *get_ball_by_id(GameData *game_data, int ball_id);