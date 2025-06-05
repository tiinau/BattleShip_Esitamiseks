package controllers;

import controllers.listeners.MyComboBoxListener;
import controllers.listeners.MyNewGameListener;
import models.GameTimer;
import models.Model;
import views.View;

import javax.swing.*;
import java.awt.event.*;

public class Controller implements MouseListener, MouseMotionListener {
    private Model model;
    private View view;
    private GameTimer gameTimer;
    private Timer guiTimer;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        gameTimer = new GameTimer(); //loome ajaobjekti, aga ei käivita

        guiTimer = new Timer(1000, e ->{
            if (gameTimer.isRunning()) {
                this.view.getLblTime().setText(gameTimer.formatGameTime());
            }
        });
        guiTimer.start(); //Käivitab GUI taimeri aga mängu aega (Gametimer) mitte!




        //Listenerid
        view.registerComboBox(new MyComboBoxListener(model, view)); //Lisab comboboxi asjad faili listeneri kaustas
        view.registerNewGameButton(new MyNewGameListener(model, view, gameTimer)); //nupu vajutuse kuulaja
    }


    @Override
    public void mouseClicked(MouseEvent e) { // seda kasutame

    }

    @Override
    public void mousePressed(MouseEvent e) { //Kasutamata meetod, aga peab olemas olema

    }

    @Override
    public void mouseReleased(MouseEvent e) { //Kasutamata meetod, aga peab olemas olema

    }

    @Override
    public void mouseEntered(MouseEvent e) { //Kasutamata meetod, aga peab olemas olema

    }

    @Override
    public void mouseExited(MouseEvent e) { //Kasutamata meetod, aga peab olemas olema

    }

    @Override
    public void mouseDragged(MouseEvent e) { //Kasutamata meetod, aga peab olemas olema

    }

    @Override
    public void mouseMoved(MouseEvent e) { // seda kasutame
        // TEST System.out.println("Liigub");
        String mouse = String.format("x=%03d & y=%03d", e.getX(), e.getY());
        view.getLblMouseXY().setText(mouse);

        // TODO Loe id, row ja col infot
        int id = model.checkGridIndex(e.getX(), e.getY());
        int row = model.getRowById(id);
        int col = model.getColById(id);
        if(id != -1) {
            view.getLblID().setText(String.valueOf(id + 1)); //Näitamine inimlikult 1 jne
        }
        //Paneb paneelile rea ja veeru numbrid
        // view.getLblRowCol().setText(String.valueOf(row + 1 + "/" + (col+1))); //minu variant
        String rowcol = String.format("%d : %d", row +1, col +1);
        if(row == -1 || col == -1) {
            rowcol = "Pole mängulaual";
        }
        view.getLblRowCol().setText(rowcol);
    }


}