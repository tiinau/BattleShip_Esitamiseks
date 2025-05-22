package models;

public class GridData {
    private int row;
    private int col;
    private int x; //Ülemine nurk
    private int y; // alumine nurk
    private int width;
    private int height;

    public GridData(int row, int col, int x, int y, int width, int height) { //Konstruktor
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    // GETTERS

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
