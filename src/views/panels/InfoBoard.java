package views.panels;

import javax.swing.*;
import java.awt.*;

public class InfoBoard  extends JPanel {

    private JPanel pnlComponent = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    // Kaks kirjastiili
    private Font fontBold = new Font("Verdana", Font.BOLD, 14);
    private Font fontNormal = new Font("Verdana", Font.PLAIN, 14);

    // Võimalikud laua suurused
    private String[] boardSizes = {"10", "11", "12", "13", "14", "15"}; // Mängulaua suuruse valikud listis (10-15)

    // Defineerime sildid (labelid), need mis muutuvad ehk parempoolsed
    private JLabel lblMouseXY; // näitab hiire koordinaate
    private JLabel lblID; // näitab kasti numbrit
    private JLabel lblRowCol; // näitab ridaxveerg
    private JLabel lblTime; // näitab mänguaega
    private JLabel lblShip; // näitab mitu laeva mitmest
    private JLabel lblGameBoard; // Näitab mängulaua suurust

    // Rippmenüü
    private JComboBox<String> cmbSize; // rippmenüü, kust saab valida mängulaua suurust

    //Nupud
    private JButton btnNewGame; // nupp uus mäng
    private JButton btnScoreBoard; // nupp edetabel

    //TODO edetabeliga seotud asjad

    public InfoBoard() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(400, 188));
        setBackground(new Color(164, 241, 248));

        pnlComponent.setBackground(new Color(244, 243, 165, 237)); // Sellele paneelile lähevad kõik labelid (hiire liikumine, laua suurus, nupud jne)

        gbc.anchor = GridBagConstraints.WEST; // Määrame kuhu need labelid tulevad - vasakule
        gbc.insets = new Insets(2, 2, 2, 2); // Määrad palju jätad ruumi ümber labelite

        //Hakkame rea kaupa neid labelid seadistama
        setupLine1();
        setupLine2();
        setupLine3();
        setupLine4();
        setupLine5();
        setupLine6();
        setupComboBox();
        setupButtons();


        add(pnlComponent); // Lisame selle kollase paneeli frame paneelile
    }

    private void setupLine1() {
        // Esimese rea esimene veerg (Hiir)
        JLabel label = new JLabel("Hiir");
        label.setFont(fontBold);
        gbc.gridx = 0; // Veerg
        gbc.gridy = 0; // Rida
        pnlComponent.add(label, gbc);

        //Esimese rea teine veerg (hiire koordinaadid)
        lblMouseXY = new JLabel("x = 0 & y = 0");
        lblMouseXY.setFont(fontNormal);
        gbc.gridx = 1; // rida = 1
        gbc.gridy = 0; // rida = 0
        pnlComponent.add(lblMouseXY, gbc);
    }

    // Rida 2
    private void setupLine2() {
        // Teise rea esimene veerg
        JLabel label = new JLabel("Lahtri ID");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlComponent.add(label, gbc);

        // Teise rea teine veerg
        lblID = new JLabel("47");
        lblID.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlComponent.add(lblID, gbc);

    }

    // Rida 3
    private void setupLine3() {
        // Kolmanda rea esimene veerg ( rida/ veerg)
        JLabel label = new JLabel("Rida : Veerg");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlComponent.add(label, gbc);
        // Kolmanda rea teine veerg
        lblRowCol = new JLabel("5 : 7");
        lblRowCol.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlComponent.add(lblRowCol, gbc);
    }

    // Rida 4
    private void setupLine4() {
        // Neljanda rea esimene veerg (aeg)
        JLabel label = new JLabel("Mängu aeg");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlComponent.add(label, gbc);

        // Neljanda rea teine veerg
        lblTime = new JLabel("00:00");
        lblTime.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlComponent.add(lblTime, gbc);
    }
    // Viies rida
    private void setupLine5() {
        // Viienda rea esimene veerg ( Laevade hulk)
        JLabel label = new JLabel("Laevad");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlComponent.add(label, gbc);

        // Viienda rea teine veerg
        lblShip = new JLabel("10 / 10");
        lblShip.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlComponent.add(lblShip, gbc);

    }
    private void setupLine6() {
        // Kuuenda rea esimene veerg ( mängulaua suurus)
        JLabel label = new JLabel("Laua suurus");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlComponent.add(label, gbc);

        // Kuuenda rea teine veerg
        lblGameBoard = new JLabel("10 x 10");
        lblGameBoard.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlComponent.add(lblGameBoard, gbc);
    }

    // Komboboxiga rea tegemine
    private void setupComboBox() {
        JLabel label = new JLabel ("Vali laua suurus");
        label.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 6;
        pnlComponent.add(label, gbc);

        // Rippmenüü loomine
        cmbSize = new JComboBox<>(boardSizes);
        cmbSize.setFont(fontNormal);
        cmbSize.setPreferredSize(new Dimension(106, 28));
        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlComponent.add(cmbSize, gbc);
    }

    // Nuppude tegemine
    private void setupButtons() {
        JLabel label = new JLabel("Nupud");
        label.setFont(fontBold);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = 2; // Ühendab kaks rida üheks
        gbc.gridwidth = 1; // Üks veerg jääb
        gbc.anchor = GridBagConstraints.WEST; // Paigutab keskele
        gbc.fill = GridBagConstraints.NONE; // Ei venita labelit
        pnlComponent.add(label, gbc);

        // Nupp uus mäng
        btnNewGame = new JButton("Uus mäng");
        btnNewGame.setFont(fontNormal);
        btnNewGame.setPreferredSize(new Dimension(106, 28));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = 1; // Tuleb see kõrgus öelda, sest seal nuppudes muutsime kõrguse ära üle kahe rea
        pnlComponent.add(btnNewGame, gbc);

        // Nupp Edetabel
        btnScoreBoard = new JButton("Edetabel");
        btnScoreBoard.setFont(fontNormal);
        btnScoreBoard.setPreferredSize(new Dimension(106, 28));
        gbc.gridx = 1;
        gbc.gridy = 8;
        pnlComponent.add(btnScoreBoard, gbc);

    }

}
