package controllers.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import models.GameTimer;
import models.Model;
import views.View;


public class MyNewGameListener implements ActionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;

    public MyNewGameListener(Model model, View view, GameTimer gameTimer) {
        this.model = model;
        this.view = view;
        this.gameTimer = gameTimer;
    }

    /**
     * Uue mänguga alustamisel uue mängu seadmine
     * mängu ajal kõik nupud pole aktiivsed (laua suuruse valik ja edatabel)
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println("TEST konsoolis tekst - Uus mäng");   // Konsooli  test
        if(!gameTimer.isRunning()) {                                // Mäng ei käi
            // See on uus lahendus, mängu kujundus taustal, mitte kohe ekraanil
            new Thread(() -> {
                model.setupNewGame();
                model.getGame().setupGameBoard();                   // Seadistame mängulaua
                model.getGame().showGameBoard();                    // Näita mängulaua sisu konsool
                view.getLblShip().setText(model.getGame().getShipsCounter()+" / " + model.getGame().getShipsParts());
                // GUI
                SwingUtilities.invokeLater(() -> {
                    view.getBtnNewGame().setText("Katkesta mäng");
                    view.getBtnScoreBoard().setEnabled(false);      // Muuda 'Edetabel' nupp mitteaktiivseks
                    view.getCmbSize().setEnabled(false);            // Muuda 'Vali laua suurus'combobox mitteaktiivseks
                    gameTimer.start();                              // Kävita aeg
                });
            }).start();

        } else { // Meil on mäng pooleli
            gameTimer.stop();
            view.getBtnNewGame().setText("Uus mäng");
            view.getBtnScoreBoard().setEnabled(true);               // Muuda 'Edetabel' nupp aktiivseks
            view.getCmbSize().setEnabled(true);                     // Muuda 'Vali laua suurus' combobox aktiivseks
        }
    }
}