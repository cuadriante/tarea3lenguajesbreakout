package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class SpeedDownBlock extends Block{

    //private final Color stroke = Color.GOLDENROD;
    final Color color = Color.LEMONCHIFFON;
    private final Color stroke = Color.CHARTREUSE;

    public SpeedDownBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    @Override
    void construct() {
        setType(3);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }

}
