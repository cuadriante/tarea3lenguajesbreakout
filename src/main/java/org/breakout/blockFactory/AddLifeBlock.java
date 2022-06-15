package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloque de agregar vida.
 */
public class AddLifeBlock extends Block{

    private final Color color = Color.ORANGE;
    //private final Color stroke = Color.ORANGERED;
    private final Color stroke = Color.CHOCOLATE;

    /**
     * Constructor de bloque que agrega vida
     * @param posX posicion en X
     * @param posY posicion en Y
     * @param width anchura
     * @param height altura
     * @param row fila
     * @param colum columna
     */
    public AddLifeBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */    @Override
    void construct() {
        setType(4);
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
