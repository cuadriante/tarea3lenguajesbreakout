package org.breakout;

// para toda la interacci[on entre el server y el cliente

public class Command {

    static int ACTION_NONE = 0;
    // to player
    static int ACTION_CREATE_PLAYER = 10; // action for player creation
    static int ACTION_MOVE_PLAYER = 11; // action for player movement
    static int ACTION_MOVE_BALL = 12; // action for ball movement
    static int ACTION_MOVE_BLOCK = 13; // action for block movement
    static int ACTION_DELETE_BLOCK = 14; // action for block deletion
    static int ACTION_SET_SCORE = 15; // action for score setting
    static int ACTION_CREATE_BLOCK = 16; // action for block creation
    static int ACTION_SET_DEPTH_LEVEL = 17; // action for depth level setting
    static int ACTION_SET_PLAYER_BAR_SIZE = 18; // action for player bar size  setting
    static int ACTION_MOVE_OTHER_PLAYER = 19; // action for other player movement setting
    // to server
    static int ACTION_START_GAME = 20; // action for game start
    static int ACTION_MOVE_LEFT = 21; // action for player movement to the left
    static int ACTION_MOVE_RIGHT = 22; // action for player movement to the right
    static int ACTION_END_GAME = 23; // action for game end
    static int ACTION_WIN_GAME = 24; // action for game end when won
    // block types
    static int BLOCK_TYPE_COMMON = 31; // action for common block
    static int BLOCK_TYPE_DOUBLE = 32; // action for double block
    static int BLOCK_TYPE_TRIPLE = 33; // action for triple block
    static int BLOCK_TYPE_INTERNAL = 34; // action for internal block
    static int BLOCK_TYPE_DEEP = 35; // action for deep block
    static int BLOCK_TYPE_SURPRISE = 36; // action for surprise block

    public int action;
    public int posX;
    public int posY;
    public int type;
    public int id;
    public int size;
    public String name;


    void setAction(int act) {
        action = act;
    }

    void setPosX(int newPlayerX) {
        posX = newPlayerX;
    }

    int getAction() {
        return action;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    void setPosY(int y) {
        posY = y;
    }

    String getName() {
        return name;
    }

    void setName(String n) {
        name = n;
    }

    int getType() {
        return type;
    }

    int getId() {
        return id;
    }

    void setId(int i) {
        id = i;
    }

    void setType(int t) {
        type = t;
    }


    int getSize() {
        return size;
    }

    void setSize(int sz) {
        size = sz;
    }
}
