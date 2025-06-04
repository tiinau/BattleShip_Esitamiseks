package models;

public class Area {
    int startRow;       // ala ülemise rea index
    int endRow;         // Ala alumise rea index
    int startCol;       // Vasakpoolse veeru index
    int endCol;         // Ala parempoolse veeru indeks

    public Area(int startRow, int endRow, int startCol, int endCol) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
    }

}
