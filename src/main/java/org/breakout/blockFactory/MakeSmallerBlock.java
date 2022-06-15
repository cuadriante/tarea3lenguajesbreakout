package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Bloque de disminucion de tamano de barra de juego.
 */
public class MakeSmallerBlock extends Block{

    private final Color color = Color.FIREBRICK;
    //private final Color stroke = Color.INDIANRED;
    private final Color stroke = Color.DEEPSKYBLUE;

    /**
     * Constructor de bloque que disminuye el tamano de la barra
     * @param posX posicion en X
     * @param posY posicion en Y
     * @param width anchura
     * @param height altura
     * @param row fila
     * @param colum columna
     */
    public MakeSmallerBlock(int posX, int posY, int width, int height, int row, int colum) {
        super(posX, posY, width, height, row, colum);
        construct();
    }

    /**
     * Cambia el tipo y color de borde
     */
    @Override
    void construct() {
        setType(2);
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
