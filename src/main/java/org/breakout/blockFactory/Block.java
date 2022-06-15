package org.breakout.blockFactory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Bloque de juego.
 */
public abstract class Block {
    private Rectangle rectangle;
    float center;
    private int id;
    private int type;
    private int color;
    private int effect;
    private int row;
    private int colum;
    private final Color stroke = Color.VIOLET;

    /**
     * Constructor de bloque
     * 
     * @param posX   posicion en x
     * @param posY   posicion en y
     * @param width  ancho
     * @param height altura
     * @param row    fila
     * @param col    columna
     */
    public Block(int posX, int posY, int width, int height, int row, int col) {
        // this.rectangle = new Rectangle(posX, posY, width, height);
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setX(posX);
        this.rectangle.setY(posY);
        this.row = row;
        this.colum = col;

    }

    abstract void construct();

    /**
     * identifica el centro edl bloque
     */
    private void setCenter() {
        this.center = (float) (this.rectangle.getX() + this.rectangle.getWidth() / 2);
        System.out.println(this.center);
    }

    /**
     * Retorna el tipo de forma del bloque
     * 
     * @return rectangulo
     */
    public Rectangle getShape() {
        return this.rectangle;
    }

    /**
     * Retorna el tipo del bloque
     * 
     * @return tipo
     */
    public int getType() {
        return type;
    }

    /**
     * Cambia el tipo al especificado
     * 
     * @param type tipo
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Retorna el id
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * retorna la fila
     * 
     * @return fila
     */
    public int getRow() {
        return row;
    }

    /**
     * Retorna la columna
     * 
     * @return columna
     */
    public int getColum() {
        return colum;
    }

    /**
     * Cambia el id al especificado
     * 
     * @param id id
     */
    public void setId(int id) {
        id = id;
    }

    /**
     * crea el color del bloque segun la fila en la que se encuentra
     * 
     * @param row fila del bloque
     */
    public void createRectangleColor(int row) {
        switch (row) {
            case (0), (1) -> setRectangleColor(Color.SEAGREEN);
            case (2), (3) -> setRectangleColor(Color.LEMONCHIFFON);
            case (4), (5) -> setRectangleColor(Color.ORANGE);
            case (6), (7) -> setRectangleColor(Color.FIREBRICK);
            default -> throw new IllegalStateException("Unexpected value: " + row);
        }
        ;

    }

    /**
     * Cambia el color del bloque
     * 
     * @param col color
     */
    public void setRectangleStroke(Color col) {
        rectangle.setArcWidth(15.0);
        rectangle.setArcHeight(10.0);
        setCenter();
        rectangle.setStroke(col);
    }

    /**
     * Cambia el color del bloque
     * 
     * @param col color
     */
    public void setRectangleColor(Color col) {
        rectangle.setArcWidth(15.0);
        rectangle.setArcHeight(10.0);
        setCenter();
        rectangle.setFill(col);

    }

    /**
     * Cambia el color de el borde de los bloques
     * 
     * @param stroke borde
     */
    public Color getStroke() {
        return stroke;
    }

}

enum col {
    GREEN, YELLOW, ORANGE, RED
}
