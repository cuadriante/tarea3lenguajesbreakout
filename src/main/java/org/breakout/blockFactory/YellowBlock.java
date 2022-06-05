package org.breakout.blockFactory;

public class YellowBlock extends Block{
    public YellowBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    @Override
    void construct() {
        setType(1);
    }
}
