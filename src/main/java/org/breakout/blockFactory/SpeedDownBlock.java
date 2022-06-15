package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloue de disminucion de velocidad de bolas.
 */
public class SpeedDownBlock extends Block {

    // private final Color stroke = Color.GOLDENROD;
    final Color color = Color.LEMONCHIFFON;
    private final Color stroke = Color.CHARTREUSE;

    /**
     * Constructor del bloque que disminuye la velocidad de la bola
     * 
     * @param posX   posicion en X
     * @param posY   posicion en Y
     * @param width  anchura
     * @param height altura
     * @param row    fila
     * @param colum  columna
     */
    public SpeedDownBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */
    @Override
    void construct() {
        setType(3);
        setRectangleStroke(stroke);
    }

    /**
     * Retorna el color
     * 
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Retorna el borde
     * 
     * @return borde
     */
    @Override
    public Color getStroke() {
        return stroke;
    }

}
