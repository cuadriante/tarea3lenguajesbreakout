package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class GreenBlock extends Block{

    public GreenBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(0);
        Color color = Color.SEAGREEN;
        Color stroke = Color.MEDIUMSEAGREEN;
        setRectangleColor(color, stroke);
    }


}
