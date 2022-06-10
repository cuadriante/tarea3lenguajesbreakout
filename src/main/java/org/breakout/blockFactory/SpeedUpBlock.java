package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class SpeedUpBlock extends Block{

    private final Color color = Color.SEAGREEN;
    //private final Color stroke = Color.MEDIUMSEAGREEN;
    private final Color stroke = Color.DEEPPINK;

    public SpeedUpBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    @Override
    void construct() {
        setType(0);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }
}
