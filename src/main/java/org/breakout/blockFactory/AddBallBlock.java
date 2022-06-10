package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class AddBallBlock extends Block{

    private final Color color = Color.ORANGE;
    //private final Color stroke = Color.ORANGERED;
    private final Color stroke = Color.VIOLET;

    public AddBallBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    @Override
    void construct() {
        setType(5);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }

}
