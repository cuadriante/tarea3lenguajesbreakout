package org.breakout;

import java.util.Arrays;

public class Adapter {
    
    /**
     * Recibe un string con los datos para crear bloques
     * y lo convierte a un array de integer.
     * Para un string "0,1,2,3":
     * 0 hace referencia a si el bloque está roto
     * 1 hace referencia a la fila del bloque
     * 2 hace referencia a la columna del bloque
     * 3 hace referencia al poderUp del bloque
     * @param data datos
     * @return un array de Integers [0,1,2,3]
     */
    public int[] stringToBlockAttributes(String data){
        String[] arrOfStr = data.split(",", -2);

        int isBreak = Integer.parseInt(arrOfStr[0]);
        int raw = Integer.parseInt(arrOfStr[1]);
        int col = Integer.parseInt(arrOfStr[2]);
        int power = Integer.parseInt(arrOfStr[3]);

        int blockAttributes[] = {isBreak, raw, col, power};
        return  blockAttributes;
    }

    /**
     * Recibe un string con los datos de una bola
     * y los convierte a un array de integer.
     * Para un string "0,1,2,3,4":
     * 0 hace referencia al id
     * 1 hace referencia a la posición en x
     * 2 hace referencia a la posición en y
     * 3 hace referencia a la velocidad en x
     * 4 hace referencia a la velocidad en y
     * @param data datos
     * @return un array de Integers [0,1,2,3]
     */
    public int[] stringToBallAttributes(String data){
        String[] arrOfStr = data.split(",", -2);
        int id = Integer.parseInt(arrOfStr[0]);
        int x = Integer.parseInt(arrOfStr[1]);
        int y = Integer.parseInt(arrOfStr[2]);
        int xSpeed = Integer.parseInt(arrOfStr[3]);
        int ySpeed = Integer.parseInt(arrOfStr[4]);

        int ballAttributes[] = {id, x, y, xSpeed, ySpeed};
        return ballAttributes;
    }

    /**
     * Parsea el dato a un int. Se una para las funciones:
     * send_score()
     * send_lives()
     * send_level()
     * @param data dato
     * @return
     */
    public int singleDatatoInt(String data){
        int dato = Integer.parseInt(data);
        return dato;
    }
}
