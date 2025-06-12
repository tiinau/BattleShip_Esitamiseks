package views.panels;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import models.GridData;
import models.Model;

public class GameBoard extends JPanel {
    private Model model;     // Anname mudeli kaasa
    private int startX = 5;  // Vasak ülemine x-koordinaat
    private int startY = 5;  // Vasak ülemine y-koordinaat
    private int width = 30;  // Ruudu laius
    private int height = 30; // Ruudu kõrgus
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"}; // Ülakomasid ei või kasutada. See on mängulaua tähestik
    private Image backgroundImage;  // Taustapilt

    public GameBoard(Model model) { // Konstruktor
        this.model = model;
        setBackground(new Color(0,190,255)); // Sinine värv
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/Background.jpg")).getImage();
            //System.out.println("Pilt olemas"); // Test
        } catch (Exception e) {
            backgroundImage = null;
           // System.out.println("Pilti ei õnnestunud laadida: " + e.getMessage()); // Test
        }
    }

    /**
     * Mängulaua suurus
     * @return
     */
    @Override
    public Dimension getPreferredSize() { // Akna tegemine, et oleks 10x10 board või 15x15 board
        int w = (width * model.getBoardSize()) + width + (2 * startX);
        int h = (height * model.getBoardSize()) + height + (2 * startY);
        return new Dimension(w, h);
    }

    /**
     * Mängulaua taust pilt või värv
     * tähed ja numbrid mängulaua ümber
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(0,190,255));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        drawColumnAlphabet(g);
        drawRowColumn(g);

        // Ruudud kui mäng on pooleli
        if(model.getGame() != null) {
            model.drawUserBoard(g);
        }
        drawGameGrid(g);
    }

    /**
     * Tühja maatriksi ruutude joonistamine
     * @param g
     */
    private void drawGameGrid(Graphics g) {
        ArrayList<GridData> matrix = new ArrayList<>(); // Tühja maatriksi tegemine
        g.setColor(Color.BLACK);

        int x = startX + width;
        int y = startY + height;
        int i = 1;

        for(int r = 0; r < model.getBoardSize(); r++) {     // Rida vasakult paremale (row)
            for(int c = 0; c < model.getBoardSize(); c++) { // Veerg ehk column
                g.drawRect(x, y, width, height);           // Joonistab ühe ruudu
                matrix.add(new GridData(r, c, x, y, width, height));
                x += width;
            }
            // Järgmise rea seaded
            y = (startY + height) + (height * i);
            i++;
            x = startX + width;
        }
        model.setGridData(matrix);
    }

    /**
     * Numbrite lisamine mängulaua äärde
     * @param g
     */
    private void drawRowColumn(Graphics g) {
        int i = 1;              // Esimene number mängulaual
        g.setColor(Color.RED);  // Numbri värv

        for(int y = startY + height; y< (height * model.getBoardSize()) + height; y += height) {
            g.drawRect(startX, y, width, height); // Joonistab ruudustiku ülevalt alla
            if (i < 10) {
                g.drawString(String.valueOf(i), startX + (width / 2) - 5, y + 2 * (startY + startY));

            } else {
                g.drawString(String.valueOf(i), startX + (width / 2)  -10, y + 2 * (startY + startY));
            }
            i++;
        }
    }

    /**
     * Tähestiku kirjutamine mängulaua äärde
     * @param g
     */
    private void drawColumnAlphabet(Graphics g) { // Tähestiku lisamise meetod
        int i = 0;                 // Tähestiku massiivi esimese tähe index A=0, B=1 jne
        g.setColor(Color.WHITE);   // Tähestiku joonistamise värv

        for (int x = startX; x<= (width * model.getBoardSize()) + width; x += width){
            g.drawRect(x, startY, width, height); // Joonista ruut 30x30
            if (x > startX) {
                g.drawString(alphabet[i], x + (width / 2) - 5, 2 * (startY + startY) + 5);
                i++;  // Suurenda tähe indeksit ühe võrra
            }
        }
    }
}