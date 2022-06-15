package org.breakout;

import java.io.BufferedReader;
import java.util.Arrays;

/**
 * Adaptador de datos de servidor a interfaz de juego.
 */
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

        int row = Integer.parseInt(arrOfStr[0]);
        int column = Integer.parseInt(arrOfStr[1]);
        int pts = Integer.parseInt(arrOfStr[2]);
        int power = Integer.parseInt(arrOfStr[3]);

        int blockAttributes[] = {row, column, pts, power};
        return  blockAttributes;
    }

    /**
     * Parsea el dato a un int. Se una para las funciones:
     * @param data dato
     * @return dato
     */
    public int singleDatatoInt(String data){
        int dato = Integer.parseInt(data);
        return dato;
    }

    /**
     * Procesa el id segun el mensaje recibido
     * @param message mensaje
     * @return id
     */
    public int processId(String message) {
        String[] arrOfStr = message.split("_");
        // System.out.print("array: ");
        // System.out.println(Arrays.toString(arrOfStr));
        String idStr = arrOfStr[0];
        // System.out.println(idStr);
        int id = Integer.valueOf(idStr);

        return id;
    }

    /**
     * Separa el id de los datos y retorna el dato.
     * @param message mensaje
     * @return datos
     */
    public String processData(String message) {
        String[] arrOfStr = message.split("_");
        String data = arrOfStr[1];

        return data;
    }

    /**
     * Separa los diferentes parametros del
     * dato recibido.
     * @param data
     * @return un array de ints con los datos
     */
    public int[] splitData(String data){
        String[] arrOfStr = data.split(",", -2);
        int[] splitData = new int[arrOfStr.length];
        for (int i = 0; i < arrOfStr.length; i++) {
            splitData[i] = Integer.parseInt(arrOfStr[i]);
        }
        return splitData;
    }
}
