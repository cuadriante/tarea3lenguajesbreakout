package org.breakout.blockFactory;

public class OrangeBlock extends Block{
    public OrangeBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
    }

    @Override
    void construct() {
        setType(0);
    }
}
