package org.breakout.blockFactory;

import javafx.scene.paint.Color;

/**
 * Fabrica de bloques de juego.
 */
public class BlockFactory {
    private static final int width = 45;
    private static final int height = 10;
    private static final int rows = 8;
    private static final int columns = 8;

    /**
     * Construtor de la fabrica de bloques
     * 
     * @param type  tipo
     * @param x     x
     * @param y     y
     * @param id    id
     * @param row   fila
     * @param colum columna
     * @return bloque
     */
    public static Block buildBlock(int type, int x, int y, int id, int row, int colum) {
        Block block = null;
        if (type == -1) {
            block = new NoPowerBlock(x, y, width, height, row, colum);
            return block;
        }
        switch (type) {
            case 0 -> block = new SpeedUpBlock(x, y, width, height, row, colum);
            case 1 -> block = new MakeSmallerBlock(x, y, width, height, row, colum);
            case 2 -> block = new MakeBiggerBlock(x, y, width, height, row, colum);
            case 3 -> block = new SpeedDownBlock(x, y, width, height, row, colum);
            case 4 -> block = new AddBallBlock(x, y, width, height, row, colum);
            case 5 -> block = new AddLifeBlock(x, y, width, height, row, colum);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        return block;
    }

    /**
     * Retorna el color segun el tipo
     * 
     * @param type tipo
     * @return color
     */
    public static col getColorByType(int type) { // esto se puede hacer mas mejor
        return switch (type) {
            case (0) -> col.GREEN;
            case (1) -> col.YELLOW;
            case (2) -> col.ORANGE;
            case (3) -> col.RED;
            default -> null;
        };
    }

    public static Color getStrokeByType(int type) { // esto se puede hacer mas mejor
        return switch (type) {
            case (-1) -> Color.WHITE;
            case (0) -> Color.DEEPPINK;
            case (1) -> Color.CYAN;
            case (2) -> Color.DEEPSKYBLUE;
            case (3) -> Color.CHARTREUSE;
            case (4) -> Color.CHOCOLATE;
            case (5) -> Color.VIOLET;
            default -> null;
        };
    }

    /**
     * Retorna las filas
     * 
     * @return filas
     */
    public static int getRows() {
        return rows;
    }

    /**
     * Retorna las columnas
     * 
     * @return columnas
     */
    public static int getColumns() {
        return columns;
    }

    /**
     * Retorna el ancho
     * 
     * @return ancho
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Retorna la altura
     * 
     * @return altura
     */
    public static int getHeight() {
        return height;
    }
}
