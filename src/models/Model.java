package models;

import java.util.ArrayList;

public class Model {
    private int boardSize = 10; // Vaikimisi laua suurus
    private ArrayList<GridData> gridData; // Loome listi

    public Model() {
        gridData = new ArrayList<>(); // See hakkab hoidma ridade, veergude, x,y kordinaatide jne infot
    }

    //GETTERS

    public int getBoardSize() {
        return boardSize;
    }

    public ArrayList<GridData> getGridData() {
        return gridData;
    }

    //SETTERS

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setGridData(ArrayList<GridData> gridData) {
        this.gridData = gridData;
    }
}
