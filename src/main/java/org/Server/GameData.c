#include <GameData.h>

GameData *start_game()
{
    GameData *game_data = malloc(sizeof(GameData));

    srand(time(0));

    int block_level = 4;
    for (int i = 0; i < BLOCK_ROWS; i++)
    {
        block_level = get_block_level(i);
        for (int j = 0; j < BLOCK_COLUMNS; j++)
        {
            int block_power_up = generate_random_powerup(1, 6);

            game_data->blocks[i][j] = malloc(sizeof(Block));
            *(game_data->blocks[i][j]) = (Block){.broken = 0,
                                                 .row = i,
                                                 .column = j,
                                                 .level = block_level,
                                                 .power_up = block_power_up};
        }
    }

    return game_data;
}

float generate_random_powerup(const int min_number, const int max_number)
{
    float prob = (float)rand() / (float)RAND_MAX;

    if (prob >= 0.7)
    {
        return rand() % max_number + min_number;
    }
    else
    {
        return -1;
    }
}

int get_block_level(int row_index)
{
    if (row_index >= 6)
    {
        return 1;
    }
    else if (row_index >= 4)
    {
        return 2;
    }
    else if (row_index >= 2)
    {
        return 3;
    }
    else
    {
        return 4;
    }
}
