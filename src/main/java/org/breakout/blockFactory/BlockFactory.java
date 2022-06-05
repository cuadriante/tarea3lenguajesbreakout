package org.breakout.blockFactory;

import org.breakout.Ball;
import org.breakout.PlayerBar;

import java.util.ArrayList;

public class BlockFactory {
    private static final int width = 40;
    private static final int height = 10;
    private static final int rows = 8;
    private static final int columns = 8;

    public static Block buildBlock(int type, int x, int y, int id){
        Block block = null;
        switch (type)
        {
            case 0 -> block = new GreenBlock(x, y, width, height);
            case 1 -> block = new RedBlock(x, y, width, height);
            case 2 -> block = new OrangeBlock(x, y, width, height);
            case 3 -> block = new YellowBlock(x, y, width, height);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        return block;
    }

    public static Color getColorByType(int type){ // esto se puede hacer mas mejor
        return switch (type) {
            case (0) -> Color.GREEN;
            case (1) -> Color.YELLOW;
            case (2) -> Color.ORANGE;
            case (3) -> Color.RED;
            default -> null;
        };
    }


    public static int getRows() {
        return rows;
    }

    public static int getColumns() {
        return columns;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}


