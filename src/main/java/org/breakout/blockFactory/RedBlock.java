package org.breakout.blockFactory;

public class RedBlock extends Block{
    public RedBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    @Override
    void construct() {
        setType(3);
    }
}
