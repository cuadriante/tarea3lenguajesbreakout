package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloque sin poder.
 */
public class NoPowerBlock extends Block {
    //private final Color stroke = Color.GOLDENROD;
    final Color color = Color.LEMONCHIFFON;
    private final Color stroke = Color.WHITE;

    /**
     * Constructor del bloque que no tiene poder
     * @param posX posicion en X
     * @param posY posicion en Y
     * @param width anchura
     * @param height altura
     * @param row fila
     * @param colum columna
     */
    public NoPowerBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */
    @Override
    void construct() {
        setType(-1);
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
