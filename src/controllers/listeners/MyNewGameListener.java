package controllers.listeners;

import models.GameTimer;
import models.Model;
import views.View;

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
        // Test  System.out.println("Uus mäng");
        if(!gameTimer.isRunning()) { // Mäng ei käi
            view.getBtnNewGame().setText("Katkesta");
            gameTimer.start();
        }else { // Meil on mäüng pooleli
            gameTimer.stop();
            view.getBtnNewGame().setText("Uus mäng");
        }
    }
}
