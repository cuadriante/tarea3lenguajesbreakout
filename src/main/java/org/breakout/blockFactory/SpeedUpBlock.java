package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloque de aumento de velocidad de bolas.
 */
public class SpeedUpBlock extends Block{

    private final Color color = Color.SEAGREEN;
    //private final Color stroke = Color.MEDIUMSEAGREEN;
    private final Color stroke = Color.DEEPPINK;

    /**
     * Constructor del bloque que aumenta la velocidad de la bola
     * @param posX posicion en X
     * @param posY posicion en Y
     * @param width anchura
     * @param height altura
     * @param row fila
     * @param colum columna
     */
    public SpeedUpBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */
    @Override
    void construct() {
        setType(0);
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
