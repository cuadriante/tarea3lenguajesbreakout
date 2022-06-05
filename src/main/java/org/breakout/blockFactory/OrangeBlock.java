package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class OrangeBlock extends Block{
    public OrangeBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(1);
        Color color = Color.ORANGE;
        Color stroke = Color.ORANGERED;
        setRectangleColor(color, stroke);
    }
}
