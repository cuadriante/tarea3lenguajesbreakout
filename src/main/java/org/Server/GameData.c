#include <GameData.h>

GameData *start_game()
{
    GameData *game_data = malloc(sizeof(GameData));

    srand(time(0));

    game_data->score = DEFAULT_SCORE;
    game_data->lives = DEFAULT_LIVES;
    game_data->level = DEFAULT_LEVEL;
    game_data->existing_balls = DEFAULT_BALL_QUANTITY;

    create_new_ball(game_data);
    generate_blocks(game_data);

    return game_data;
}

void generate_blocks(GameData *game_data)
{
    int block_level;
    int block_power_up;
    for (int i = 0; i < BLOCK_ROWS; i++)
    {
        block_level = get_block_level(i);
        for (int j = 0; j < BLOCK_COLUMNS; j++)
        {
            block_power_up = generate_random_powerup(1, 6);

            game_data->blocks[i][j] = malloc(sizeof(Block));
            *(game_data->blocks[i][j]) = (Block){.row = i,
                                                 .column = j,
                                                 .level = block_level,
                                                 .power_up = block_power_up};
        }
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

int generate_random_powerup(const int min_number, const int max_number)
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

void create_new_ball(GameData *game_data)
{
    for (int i = 0; i < MAX_BALLS; i++)
    {
        if (i == (game_data->existing_balls))
        {
            game_data->balls[i] = malloc(sizeof(Ball));
            game_data->balls[i]->id = i + 1;
            game_data->balls[i]->pos_x = DEFAULT_POS_X;
            game_data->balls[i]->pos_y = DEFAULT_POS_Y;
            game_data->balls[i]->speed_x = DEFAULT_SPEED_X;
            game_data->balls[i]->speed_y = DEFAULT_SPEED_Y;

            game_data->existing_balls += 1;

            break;
        }
    }
}