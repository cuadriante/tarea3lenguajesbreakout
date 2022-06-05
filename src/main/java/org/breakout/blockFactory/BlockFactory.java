package org.breakout.blockFactory;

public class BlockFactory {
    private static final int width = 45;
    private static final int height = 10;
    private static final int rows = 8;
    private static final int columns = 8;

    public static Block buildBlock(int type, int x, int y, int id){
        Block block = null;
        switch (type)
        {
            case 0 -> block = new SpeedUpBlock(x, y, width, height);
            case 1 -> block = new MakeSmallerBlock(x, y, width, height);
            case 2 -> block = new MakeBiggerBlock(x, y, width, height);
            case 3 -> block = new SpeedDownBlock(x, y, width, height);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        return block;
    }

    public static col getColorByType(int type){ // esto se puede hacer mas mejor
        return switch (type) {
            case (0) -> col.GREEN;
            case (1) -> col.YELLOW;
            case (2) -> col.ORANGE;
            case (3) -> col.RED;
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


