package org.breakout.blockFactory;

public class GreenBlock extends Block{

    public GreenBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    @Override
    void construct() {
        setType(0);
    }
}
