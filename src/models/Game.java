package models;

import java.util.Random;
import java.util.stream.IntStream;

public class Game {
    private int boardSize;          // Mängulaua suurus vaikimisi 10x10
    private int[][] boardMatrix;    // Mängulaual asuvad laevad
    private Random random = new Random();

    //private int[] ships = {2, 1};   // TEST KAHE LAEVAGA
    private int[] ships = {5, 4, 3, 3, 2, 2, 2, 1, 1, 1, 1};    // Laevade valik
    //private int[] ships = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1};    // Laevade valik

    private int shipsCounter = 0;
    private int clickCounter = 0; // Klikkide lugemine

    public Game(int boardSize) {
        this.boardSize = boardSize;
        this.boardMatrix = new int[boardSize][boardSize];
    }

    /**
     * Näita konsoolis mängulaua sisu
     */
    public void showGameBoard() {
        System.out.println();           // Tühi rida
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                //System.out.print(boardMatrix[row][col] + " ");
            }
            System.out.println(); // Veeru lõpus uuele reale
        }
    }

    public void setupGameBoard() {
        boardMatrix = new int[boardSize][boardSize]; // Uus laua suurus ( algseis 0)
        int shipsTotal = ships.length;               // Kui palju on laevu kokku
        int shipsPlaced = 0;                         // Kui palju on laevu paigutatud
        // Laevade järjekorra segamine
        while (shipsPlaced < shipsTotal) {
            int length = ships[shipsPlaced];  // Millist laeva paigutada (laeva pikkus)
            boolean placed = false;           // Laevu pole paigutatud

            // Valime juhusliku rea ja veeru
            int startRow = random.nextInt(boardSize);   // Rida
            int startCol = random.nextInt(boardSize);   // Veerg

            // Käime kogu laua läbi alates sellest punktist
            outerLoop:
            // Lihtsalt silt (label ) "for loop" - ile
            for (int rOffset = 0; rOffset < boardSize; rOffset++) {     // Rida
                int r = (startRow + rOffset) % boardSize;
                for (int cOffset = 0; cOffset < boardSize; cOffset++) { // Veerg
                    int c = (startCol + cOffset) % boardSize;

                    boolean vertical = random.nextBoolean(); // Määrame juhusliku suuna
                    if (tryPlaceShip(r, c, length, vertical ) || tryPlaceShip(r, c, length, !vertical)) {
                        placed = true;      // Laev paigutatud
                        break outerLoop;    // Katkesta mõlemad for loop kordused
                    }
                }
            }
            if (placed) {
                shipsPlaced++;

            } else {
                setupGameBoard();
                return;
            }
        }
        // Eemaldame ajutised kitsetsoonid (9-d), jätte alles ainult laevad (1-4) ja tühjad vee kohad
        replaceNineToZero();
    }

    private void replaceNineToZero() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (boardMatrix[row][col] == 9) {
                    boardMatrix[row][col] = 0;
                }
            }
        }
    }

    private boolean tryPlaceShip(int row, int col, int length, boolean vertical) {
        // Kontrolli kas laev mahub mängulauale
        if (vertical && row + length > boardSize) return false;
        if (!vertical && col + length > boardSize) return false;

        // Kontrolli kas piirkond on vaba (kaitsetsoon)
        if (!canPlaceShip(row, col, length, vertical)) return false;

        // Kirjutame laeva mängualuale : paigutame igasse lahtrisse laeva pikkuse
        for (int i = 0; i < length; i++) {
            int r = vertical ? row + i : row;  // Kasutame rida või mitte, sõltub suunast kas laev on vertik või horisont
            int c = vertical ? col : col + i;  // Sama veeru kohta
            boardMatrix[r][c] = length;        // Määrame laeva lahtrisse selle mpikkuse
        }
        // Laeva ümber kaitsetsooni lisamine (laevad ei puuduta teineteist)
        makeSurrounding(row, col, length, vertical);
        return true;
    }

    private void makeSurrounding(int row, int col, int length, boolean vertical) {
        Area area = getShipSurroundingArea(row, col, length, vertical);
        // Käime ala iga s lahtris ja kui seal on vesi (0) , siis märgime selle kaitseks (9)
        for (int r = area.startRow; r <= area.endRow; r++) {
            for (int c = area.startCol; c <= area.endCol; c++) {
                if (boardMatrix[r][c] == 0) { // Kas on vesi
                    boardMatrix[r][c] = 9; // Pane kaitse
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean vertical) {
        Area area = getShipSurroundingArea(row, col, length, vertical); // Saame laeva ümbritseva ala
        // Kontrollime igat lahtrit alal  - kuskil pole tühjust, (0), katkestame
        for (int r = area.startRow; r <= area.endRow; r++) {
            for (int c = area.startCol; c <= area.endCol; c++) {
                if (boardMatrix[r][c] > 0 && boardMatrix[r][c] <=5) return false; // Midagi on ees, ei sobi
            }
        }
        return true;  // Kõik kohad olid vabad
    }


    private Area getShipSurroundingArea(int row, int col, int length, boolean vertical) {
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(boardSize - 1, vertical ? row + length : row + 1);
        int startCol = Math.max(0, col - 1);
        int endCol = Math.min(boardSize - 1, vertical ? col + 1 : col + length);
        return new Area(startRow, endRow, startCol, endCol);
    }

    /**
     * Selles lahtris klikkis kasutaja hiirega, kas sai piuhta või läks mööda
     * @param row rida
     * @param col veerg
     * @param what millega tegu (7 pihtas, 8 mööda)
     */
    public  void setUserClick(int row, int col, int what) {
        if (what== 7) {
            boardMatrix[row][col] = 7; // Pihtas
        }else if (what== 8) {
            boardMatrix[row][col] = 8; // Möödas
        }
    }

    // GETTERS

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    public int getShipsCounter() {
        return shipsCounter;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    /**
     * (4,3,3,jne) laevade summa näide on 10(4,3,3)
     * @return laevade pikkuste summa
     */
    public int getShipsParts(){
        return IntStream.of(ships).sum();
    }

    /**
     * Kas mäng on läbi
     * @return true -läbi ja false- ei ole läbi
     */
    public boolean isGameOver() {
        return getShipsParts() == getShipsCounter(); // Võrdsed siis 'true' ja rinev siis 'false'
    }


    // SETTERS

    /**
     * Suurendab leitud laevade hulka etteantud väärtuse võrra
     * @param shipsCounter etteantud väärtus (1)
     */
    public void setShipsCounter(int shipsCounter) { // Kui leitakse , siis kasvab ühe võrra
        this.shipsCounter += shipsCounter;          // Kasvamine sõltub sellest, mis väärtus enne oli
    }

    /**
     * Suurendab leitud klikkide hulka etteantud väärtuse võrra
     * @param clickCounter
     */
    public void setClickCounter(int clickCounter) { // Kui leitakse kasvab ühe võrra
        this.clickCounter += clickCounter;          // Kasvamine sõltub sellest, mis väärtus enne oli
    }
}
