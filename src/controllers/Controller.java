package controllers;

import controllers.listeners.MyComboBoxListener;
import controllers.listeners.MyNewGameListener;
import controllers.listeners.MyScoreBoardListener;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import models.Database;
import models.GameTimer;
import models.Model;
import views.View;

public class Controller implements MouseListener, MouseMotionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;
    private Timer guiTimer;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        gameTimer = new GameTimer(); // Loome ajaobjekti, aga ei käivita

        guiTimer = new Timer(1000, e ->{
            if (gameTimer.isRunning()) {
                this.view.getLblTime().setText(gameTimer.formatGameTime());
            }
        });
        guiTimer.start(); // Käivitab GUI taimeri aga mängu aega (Gametimer) mitte!

        // Listenerid
        view.registerComboBox(new MyComboBoxListener(model, view));                // Lisab comboboxi asjad faili listeneri kaustas
        view.registerNewGameButton(new MyNewGameListener(model, view, gameTimer)); // Nupu vajutuse kuulaja
        view.registerScoreBoardButton(new MyScoreBoardListener(model, view));
    }

    /**
     * Hiirega mängulaual klikkimine
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) { // Kasutame
        if(gameTimer.isRunning()) {         // Kas toimub mäng
            // Kuhu klikiti hiirega
            int id = model.checkGridIndex(e.getX(), e.getY());
            int row = model.getRowById(id);
            int col = model.getColById(id);
            // Hetke laud
            int[][] matrix = model.getGame().getBoardMatrix();
            model.getGame().setClickCounter(1); // Kliki lugeja
            if(matrix[row][col] == 0) {         // 0 on vesi, ehk mööda
                model.getGame().setUserClick(row,col,8);
                //view.getLblShip().setText(String.format("%d / %d", model.getGame().getShipsCounter(), model.getGame().getShipsParts()));
            } else if (matrix[row][col] >= 1 && matrix[row][col] <= 5) { // Laevale pihtas
                model.getGame().setUserClick(row,col,7);
                model.getGame().setShipsCounter(1);  // Laeva osade leidmine
                view.getLblShip().setText(String.format("%d / %d", model.getGame().getShipsCounter(), model.getGame().getShipsParts()));
            }
            // Näita konsooli mängulauda
            model.getGame().showGameBoard();
            // Uuenda joonsitust
            view.repaint();
            // Kontrolli mängu lõppu
            checkGameOver();
        }
    }

    /**
     * Kontrollib kas mäng kestab.
     * Küsib mängu lõppedes mängija nime
     * Lisab mängija edetabelisse
     */
    private void checkGameOver(){
        if(model.getGame() != null && model.getGame().isGameOver()) {
            gameTimer.stop();                           // Peata aeg
            view.getBtnNewGame().setText("Uus mäng");   // Nupu tekst muutub tagasi: enne "Katkesta", pärast "uus mäng"
            view.getBtnScoreBoard().setEnabled(true);   // Muuda 'Edetabel' nupp aktiivseks
            view.getCmbSize().setEnabled(true);         // Muuda 'Vali laua suurus' combobox aktiivseks

            // JOptionPane.showMessageDialog(view, "Mängu aeg: " + gameTimer.formatGameTime()); // Teatab mängu lõpus
            // Küsime kasutaja nime
            String name = JOptionPane.showInputDialog(view, "Kuidas on mängija nimi?", "Mäng on läbi", JOptionPane.INFORMATION_MESSAGE);
            if (name == null){                         // Cancel või akna sulgemine
                name = "Teadmata";
            }
            if(name.trim().isEmpty()) {
                name = "Teadmata";                      // Kui kasutaja ei sisestanud nime
            }
            // Edetabeli faili ja andmebaasi lisamine (kaks eraldi rida)
            saveEntryToFile(name.trim());               // Edetabeli faili kirjutamine
            saveEntryToTable(name.trim());              // Andmebaasi kirjutamine
        }
    }

    /**
     * Salvestab tulemsue andmebaasi
     * lisab nime, aja, klikkide arvu, laua suuruse, kuupäeva
     * @param name
     */
    private void saveEntryToTable(String name) {
        try(Database db = new Database(model)){
            db.insert(name, gameTimer.getElapsedSeconds(), model.getGame().getClickCounter(),
                    model.getBoardSize(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Salvestab tulemuse faili
     * lisab nime, aja, klikkide arvu, laua suuruse, kuupäeva
     * @param name
     */
    public void saveEntryToFile(String name) {
        if(model.checkFileExistsAndContent()){ // NB!
            // Fail on olemas, kirjutame sisu faili
            File file = new File(model.getScoreFile());
            try(FileWriter fw = new FileWriter(file, true)){
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                int time = gameTimer.getElapsedSeconds();
                int clicks = model.getGame().getClickCounter();
                int board = model.getBoardSize();
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String datLine = String.join(";", name, String.valueOf(time), String.valueOf(clicks),  String.valueOf(board), dateTime);
                pw.println(datLine); // Kirjuta faili
                pw.close();  // Sulge fail
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {  // Edetabeli faili ei leitud või sisu puudub
            File file = new File(model.getScoreFile());
            try(FileWriter fw = new FileWriter(file, true)){
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(String.join(";", model.getColumnNames())); // UUS
                int time = gameTimer.getElapsedSeconds();
                int clicks = model.getGame().getClickCounter();
                int board = model.getBoardSize();
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String datLine = String.join(";", name, String.valueOf(time), String.valueOf(clicks),  String.valueOf(board), dateTime);
                pw.println(datLine);        // Kirjuta faili
                pw.close();                 // Sulge fail
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { // Kasutamata meetod, aga peab olemas olema
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Kasutamata meetod, aga peab olemas olema
    }

    @Override
    public void mouseEntered(MouseEvent e) { // Kasutamata meetod, aga peab olemas olema
    }

    @Override
    public void mouseExited(MouseEvent e) { // Kasutamata meetod, aga peab olemas olema
    }

    @Override
    public void mouseDragged(MouseEvent e) { // Kasutamata meetod, aga peab olemas olema
    }

    @Override
    public void mouseMoved(MouseEvent e) {  // Seda kasutame
        // TEST System.out.println("Liigub");
        String mouse = String.format("x=%03d & y=%03d", e.getX(), e.getY());
        view.getLblMouseXY().setText(mouse);

        // Loe id, row ja col infot
        int id = model.checkGridIndex(e.getX(), e.getY());
        int row = model.getRowById(id);
        int col = model.getColById(id);
        if(id != -1) {
            view.getLblID().setText(String.valueOf(id + 1)); //Näitamine inimlikult 1 jne
        }
        // Paneb paneelile rea ja veeru numbrid
        // view.getLblRowCol().setText(String.valueOf(row + 1 + "/" + (col+1))); //minu variant
        String rowcol = String.format("%d : %d", row +1, col +1);
        if(row == -1 || col == -1) {
            rowcol = "Pole mängulaual";
        }
        view.getLblRowCol().setText(rowcol);
    }
}