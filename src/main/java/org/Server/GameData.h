#pragma once

#include <stdlib.h>
#include <stdbool.h>

#include "constants.h"

typedef struct
{
    int broken;
    int row;
    int column;
    int level;
    int power_up;
} Block;

typedef struct
{
    Block *blocks[BLOCK_ROWS][BLOCK_COLUMNS];
} GameData;

GameData *start_game();
int get_block_level(int row_index);
float generate_random_powerup(const int min_number, const int max_number);