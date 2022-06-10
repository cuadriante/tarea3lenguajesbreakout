package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class MakeSmallerBlock extends Block{

    private final Color color = Color.FIREBRICK;
    //private final Color stroke = Color.INDIANRED;
    private final Color stroke = Color.DEEPSKYBLUE;

    public MakeSmallerBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    @Override
    void construct() {
        setType(2);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }

}
