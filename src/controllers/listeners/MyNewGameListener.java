package controllers.listeners;

import models.GameTimer;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyNewGameListener implements ActionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;

    public MyNewGameListener(Model model, View view, GameTimer gameTimer) {
        this.model = model;
        this.view = view;
        this.gameTimer = gameTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Test  System.out.println(" konsoolis tekst - Uus mäng");
        if(!gameTimer.isRunning()) { // Mäng ei käi
            // See on uus lahendus , mängu kujundus taustal, mitte kohe ekraanil
            new Thread(() -> {
                model.setupNewGame();
                model.getGame().setupGameBoard();       // seadistrame mängulaua
                model.getGame().showGameBoard();        // näita mängulaua sisu konsool
                view.getLblShip().setText(model.getGame().getShipsCounter()+" / " + model.getGame().getShipsParts());
                // GUI
                SwingUtilities.invokeLater(() -> {
                    view.getBtnNewGame().setText("Katkesta mäng");
                    gameTimer.start(); // Kävita aeg
                });
            }).start();

        }else { // Meil on mäüng pooleli
            gameTimer.stop();
            view.getBtnNewGame().setText("Uus mäng");
        }
    }
}
