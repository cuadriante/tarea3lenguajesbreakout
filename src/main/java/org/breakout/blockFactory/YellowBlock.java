package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class YellowBlock extends Block{
    public YellowBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(3);
        Color stroke = Color.GOLDENROD;
        Color color = Color.LEMONCHIFFON;
        setRectangleColor(color, stroke);
    }
}
