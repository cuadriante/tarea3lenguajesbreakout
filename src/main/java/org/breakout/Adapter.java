package org.breakout;

public class Adapter {
    
    public void stringToBlockAttributes(String data){
        String[] arrOfStr = data.split(",", -2);
        int isBreak = Integer.parseInt(arrOfStr[0]);
        int raw = Integer.parseInt(arrOfStr[1]);
        int col = Integer.parseInt(arrOfStr[2]);
        int power = Integer.parseInt(arrOfStr[3]);
    }
    
}
