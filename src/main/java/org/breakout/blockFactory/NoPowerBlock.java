package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class NoPowerBlock extends Block {
    //private final Color stroke = Color.GOLDENROD;
    final Color color = Color.LEMONCHIFFON;
    private final Color stroke = Color.WHITE;

    public NoPowerBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(-1);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }
}
