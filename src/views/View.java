package views;

import controllers.Controller;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.*;
import models.Model;
import views.panels.GameBoard;
import views.panels.InfoBoard;

public class View extends JFrame {
    private Model model;
    private GameBoard gameBoard;
    private InfoBoard infoBoard;
    private JPanel leaderboardPanelRef; // Uus paneel edetabeli vaatamiseks
    private JPanel container;           // Edetabeli vaatamiseks samas aknas paremal

    public View(Model model) {
        super("Laevade pommitamine");
        this.model = model;
        this.gameBoard = new GameBoard(model);

        infoBoard = new InfoBoard();  // Mängu info laevade kohta... kollane ala

        container = new JPanel(new BorderLayout()); // Edetabelit ei vaata eraldi akanas, siis kasutame
        container.add(gameBoard, BorderLayout.CENTER);
        container.add(infoBoard, BorderLayout.EAST);

        add(container);
        setMinimumSize(gameBoard.getPreferredSize());

        // TEST Frame ja Panel Layout Managerid
//        System.out.println("JFrame:        " + this.getLayout());
//        System.out.println("container:     " + container.getLayout());
//        System.out.println("GameBoard:     " + gameBoard.getLayout());
//        System.out.println("InfoBoard:     " + infoBoard.getLayout());
//        System.out.println("pnlComponents: " + infoBoard.getPnlComponent().getLayout());
    }

    public JLabel getLblMouseXY() {
        return infoBoard.getLblMouseXY();
    }

    public JLabel getLblID() {
        return infoBoard.getLblID();
    }

    public JLabel getLblRowCol() {
        return infoBoard.getLblRowCol();
    }

    public JLabel getLblTime() {
        return infoBoard.getLblTime();
    }

    public JLabel getLblShip() {
        return infoBoard.getLblShip();
    }

    public JLabel getLblGameBoard() {
        return infoBoard.getLblGameBoard();
    }

    public JComboBox<String> getCmbSize() {
        return infoBoard.getCmbSize();
    }

    public JButton getBtnNewGame() {
        return infoBoard.getBtnNewGame();
    }

    public JButton getBtnScoreBoard() {
        return infoBoard.getBtnScoreBoard();
    }

    public JRadioButton getRdoFile() {
        return infoBoard.getRdoFile();
    }

    public JRadioButton getRdoDb() {
        return infoBoard.getRdoDb();
    }

    public JCheckBox getChcWhere() {
        return infoBoard.getChcWhere();
    }

    public void registerGameBoardMouse(Controller controller) {
        gameBoard.addMouseListener(controller);                 // Hiire kuulamine. Võtame mängulaualt selle hiire liikumise
        gameBoard.addMouseMotionListener(controller);
    }
    public void registerComboBox(ItemListener itemListener) {   // Registreerib endale selle valiku, mis on tehtud comboboxis
        infoBoard.getCmbSize().addItemListener(itemListener);   // Hiire liikumine
    }

    public void registerNewGameButton(ActionListener actionListener) {
        infoBoard.getBtnNewGame().addActionListener(actionListener); // Nupu klikimise funktsionaalsus
    }

    public void registerScoreBoardButton(ActionListener actionListener) {
        infoBoard.getBtnScoreBoard().addActionListener(actionListener);
    }

    // Peaaknas Edetabeli vaatamine mille juures on sulgemiseks nupp
    public void showLeaderboardInMainWindow(JPanel leaderboardPanel) {
        leaderboardPanel.setLayout(new FlowLayout());
        JButton closeBtn = new JButton("Sulge");
        leaderboardPanel.add(closeBtn);

        // Ajutine InfoBoard eemaldus Edetabeli vaatamise ajal samas aknas
        container.remove(infoBoard);
        // Eemalda eelmine edetabel, kui pole tühi
        if (leaderboardPanelRef != null) {
            container.remove(leaderboardPanelRef);
        }
        container.add(leaderboardPanel, BorderLayout.EAST);
        leaderboardPanelRef = leaderboardPanel;
        container.revalidate();
        container.repaint();

        closeBtn.addActionListener(e -> {
            container.remove(leaderboardPanelRef);
            container.add(infoBoard, BorderLayout.EAST);
            container.revalidate();
            container.repaint();
        });
    }
}