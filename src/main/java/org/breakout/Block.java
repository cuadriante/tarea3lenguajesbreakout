package org.breakout;

public class Block {

    private int id;
    private int type;
    private int hitsToBreak;

    //Block(QGraphicsItem *parent) {
     //   setRect(0,0,100,15);

   // }


    int getType() {
        return type;
    }

    void setType(int type) {
    }

    int getHitsToBreak() {
        return hitsToBreak;
    }

    void setHitsToBreak(int hitsToBreak) {
        hitsToBreak = hitsToBreak;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        id = id;
    }

}
