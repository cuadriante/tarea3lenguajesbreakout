package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class RedBlock extends Block{
    public RedBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(2);
        Color color = Color.FIREBRICK;
        Color stroke = Color.INDIANRED;
        setRectangleColor(color, stroke);
    }
}
