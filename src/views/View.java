package views;

import models.Model;
import views.panels.GameBoard;
import views.panels.InfoBoard;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Model model;
    private GameBoard gameBoard; // Mängulaud
    private InfoBoard infoBoard; // Infotahvel

    public View(Model model) {
        super("Laevade pommitamine"); // Pealkiri, supper laseb kogu frame sisu kasutada
        this.model = model;

        gameBoard = new GameBoard(model); // Loome mängulaua
        infoBoard = new InfoBoard(); // Loome infotahvli

        JPanel container = new JPanel(new BorderLayout()); // Loome uue paneeli
        container.add(gameBoard, BorderLayout.CENTER); // Paneme paneeli peale mängulaua ujuvale osale
        container.add(infoBoard, BorderLayout.EAST); // Paneme paneeli peale infotahvli idasse

        add(container); // Paneme konteineri peale

        //TEST Frame ja Panel Layout Managerid
        System.out.println("JFrame:        " + this.getLayout());
        System.out.println("container:     " + container.getLayout());
        System.out.println("GameBoard:     " + gameBoard.getLayout());
        System.out.println("InfoBoard:     " + infoBoard.getLayout());
        System.out.println("pnlComponents: " + infoBoard.getPnlComponent().getLayout());
    }
}
