package org.breakout;

// para toda la interacci[on entre el server y el cliente

/**
 * Comandos de comunicacion
 */
public class Command {
    public int action;

    public int posX;
    public int posY;
    public int type;
    public int id;
    public int size;
    public String name;

    /**
     * Cambia la accion a la especificada
     * @param act accion
     */
    void setAction(int act) {
        action = act;
    }

    /**
     * Cambia la posicion en X a la especificada
     * @param newPlayerX posicion en X del jugador
     */
    void setPosX(int newPlayerX) {
        posX = newPlayerX;
    }

    /**
     * Retorna accion
     * @return accion
     */
    int getAction() {
        return action;
    }

    /**
     * Retorna posicion en el eje X
     * @return posicion en el eje X
     */
    int getPosX() {
        return posX;
    }

    /**
     * Retorna posicion en el eje Y
     * @return posicion en el eje Y
     */
    int getPosY() {
        return posY;
    }

    /**
     * Cambia la posicion en Y a la especificada
     * @param y posicion en Y
     */
    void setPosY(int y) {
        posY = y;
    }

    /**
     * Retorna el nombre
     * @return nombre
     */
    String getName() {
        return name;
    }

    /**
     * Cambia el nombre al especificado
     * @param n nombre
     */
    void setName(String n) {
        name = n;
    }

    /**
     * Retorna el tipo
     * @return tipo
     */
    int getType() {
        return type;
    }

    /**
     * Retorna el id
     * @return id
     */
    int getId() {
        return id;
    }

    /**
     * Cambia el id
     * @param i id
     */
    void setId(int i) {
        id = i;
    }

    /**
     * Cambia el tipo
     * @param t tipo
     */
    void setType(int t) {
        type = t;
    }

    /**
     * retorna el tamano
     * @return tamano
     */
    int getSize() {
        return size;
    }

    /**
     * Cambia el tamano
     * @param sz tamano
     */
    void setSize(int sz) {
        size = sz;
    }
}
