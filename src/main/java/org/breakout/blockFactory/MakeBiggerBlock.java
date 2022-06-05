package org.breakout.blockFactory;

import javafx.scene.paint.Color;

public class MakeBiggerBlock extends Block{

    private final Color color = Color.ORANGE;
    //private final Color stroke = Color.ORANGERED;
    private final Color stroke = Color.CYAN;

    public MakeBiggerBlock(int posX, int posY, int width, int height) {
        super(posX, posY, width, height);
        construct();
    }

    @Override
    void construct() {
        setType(1);
        setRectangleStroke(stroke);
    }

    public Color getColor() {
        return color;
    }

    public Color getStroke() {
        return stroke;
    }

}
