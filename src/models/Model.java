package models;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Model {
    private int boardSize = 10; // Vaikimisi laua suurus
    private ArrayList<GridData> gridData; // Loome listi
    private Game game;
    // Edetabeli failiga seotud muutujad
    private String scoreFile = "scores.txt";
    private String[] columnNames = new String[]{"Nimi", "Aeg", "Klikke", "Laua suurus", "Mängu aeg"};

    public Model() {
        gridData = new ArrayList<>(); // See hakkab hoidma ridade, veergude, x,y kordinaatide jne infot
    }

    /**
     * Tagastab hiire koordinaatide põhjal massiivi indeksi ehk id
     * @param mouseX hiire X koordinaat
     * @param mouseY hiire Y koordinaat
     * @return tagastab lahtri ID
     */
    public int checkGridIndex(int mouseX, int mouseY) { //Kontrollib hiire kordinaate
        int result = -1; //Viga
        int index = 0; //Alguspunkt on 0
        for(GridData gd : gridData) {
            if(mouseX > gd.getX() && mouseX <=(gd.getX() + gd.getWidth()) && mouseY > gd.getY() && mouseY <= (gd.getY() + gd.getHeight())) {
                result = index;
            }
            index++; // Index kasvab
        }
        return result; //Lahtri ID
    }

    /**
     * Tagastab mängulaua reanumbri saadud id põhjal (checkGridIndex)
     * @param id mängulaualaua id
     * @return tagastab rea numbri
     */
    public int getRowById(int id) {
        if(id != -1) { // Kui ei ole -1
            return gridData.get(id).getRow();
        }
        return -1; //Viga
    }

    /**
     * Tagastab mängulaua veerunumbri saadud id põhjal
     * @param id mängulaua id
     * @return tagastab veeru numbri
     */
    public int getColById(int id) {
        if(id != -1) { // Kui ei ole -1
            return gridData.get(id).getCol();
        }
        return -1; //Viga
    }

    public void setupNewGame(){
        game = new Game(boardSize);
    }

    public void drawUserBoard(Graphics g) { // antakse kaasa joonistuslaud
        ArrayList<GridData> gdList = getGridData(); // See loodi laua joonistamisel
        int[][] matrix = game.getBoardMatrix(); // See on laevade, vee jm info (0, 1-5, 7, 8)

        for(GridData gd : gdList) {
            int row = gd.getRow(); // Rida
            int col = gd.getCol(); // Veerg
            int cellValue = matrix[row][col]; // Väärtus: 0, 1-5, 7, 8

            // Määrame värvi ja suuruse sõltuvalt lahtri väärtusest (cellValue)
            Color color = null;  // Algset värvi pole
            int padding = 0;

            switch(cellValue) { // 0, 1-5,7, 8
                case 0: // Vesi
                    color = new Color(0,190,255);
                    break;
                case 7:
                    color = Color.GREEN; // Laev
                    break;
                case 8:
                    color = Color.RED;
                    padding = 3;
                    break;
                default:
                    if(cellValue >=1 && cellValue <=5) { // laevad 1-5
                        // Kommewnteeri välja kui ei soovi laevu mängulaual näha
                        //color = new Color(236, 236, 137); // siin asuvad laevad tegelikult SOBI TEGEMISEKS
                        color = new Color(0,190,255); // siin asuvad laevad tegelikult


                    }

            }

            // Kui värv on määratud, joonista ruut
            if(color != null) {
                g.setColor(color);
                g.fillRect(
                        gd.getX()+padding,
                        gd.getY()+padding,
                        gd.getWidth()-2 * padding,
                        gd.getHeight()-2 * padding
                );
            }
        }
    }

    /**
     * Edetabeli faili olemasolu ja sisu kontroll
     * @return true kui korras ja false kui pole
     */
    public boolean checkFileExistsAndContent(){
        File file = new File(scoreFile);
        if(!file.exists()) { // Kui faili pole, siis tagastab false
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(scoreFile))) {
            String line = br.readLine(); // Loeme rea
            if(line == null) {
                return false; // Ridu pole üldse
            }

            String[] columns = line.split(";");
            return columns.length == columnNames.length;  // Lihtsustatud if lause



        } catch (IOException e) {
            // throw new RuntimeException(e);
            return false;
        }
    }



    //GETTERS

    public int getBoardSize() {
        return boardSize;
    }

    public ArrayList<GridData> getGridData() {
        return gridData;
    }

    public Game getGame() {
        return game;
    }

    public String getScoreFile() {
        return scoreFile;
    }

    public String[] getColumnNames() {
        return columnNames;
    }


    //SETTERS

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setGridData(ArrayList<GridData> gridData) {
        this.gridData = gridData;
    }
}
