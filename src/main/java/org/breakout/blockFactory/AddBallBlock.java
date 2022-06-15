package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloque de agregar bola.
 */
public class AddBallBlock extends Block{

    private final Color color = Color.ORANGE;
    //private final Color stroke = Color.ORANGERED;
    private final Color stroke = Color.VIOLET;

    /**
     * Constructor de bloque que agrega bola
     * @param posX posicion en X
     * @param posY posicion en Y
     * @param width anchura
     * @param height altura
     * @param row fila
     * @param colum columna
     */
    public AddBallBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */
    @Override
    void construct() {
        setType(5);
        setRectangleStroke(stroke);
    }

    /**
     * Retorna el color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retorna el borde
     * @return borde
     */
    public Color getStroke() {
        return stroke;
    }

}
