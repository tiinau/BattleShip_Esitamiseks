package controllers.listeners;

import models.Database;
import models.Model;
import models.ScoreData;
import views.View;
import views.dialogs.ScoreBoardDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MyScoreBoardListener implements ActionListener {
    private Model model;
    private View view;
    private JDialog dlgScoreBoard; // Edetabeli aken (JDialog)

    public MyScoreBoardListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(" Edetabel nuppu vajutati ");
        ArrayList<ScoreData> result;
        if (view.getRdoFile().isSelected()) { // File
            result = model.readFromFile();  // Loe faili sisu massiivi
            if (createTable(result)) {
                setupDlgScoreBoard();
            } else {
                JOptionPane.showMessageDialog(view, "Andmeid pole");
            }
        } else { // Andmebaasi osa
            try (Database db = new Database(model)) {
                result = db.select(model.getBoardSize());
                if (!result.isEmpty() && createTableDb(result)) {
                    setupDlgScoreBoard();
                } else {
                    JOptionPane.showMessageDialog(view, "Andmbaasi tabel on tühi");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private boolean createTableDb(ArrayList<ScoreData> result) {
        if (!result.isEmpty()) {
            String[][] data = new String[result.size()][5]; // Veergude arv on meil tabelis 5
            for (int i = 0; i < result.size(); i++) {
                data[i][0] = result.get(i).getName();
                data[i][1] = result.get(i).formatGameTime(result.get(i).getTime());
                data[i][2] = String.valueOf(result.get(i).getClicks());
                data[i][3] = String.valueOf(result.get(i).getBoard());
                data[i][4] = result.get(i).getPlayedTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            }
            // Loome read-only TableModel(topelklikk lahtris võimatu)
            DefaultTableModel tableModel = new DefaultTableModel(data, model.getColumnNames()) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;  // Ei luba lahtri sisu muuta
                }
            };
            JTable table = new JTable(tableModel);

            // Tabeli klikkimine
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && !e.isConsumed()) {
                        e.consume();
                        int row = table.rowAtPoint(e.getPoint());
                        int col = table.columnAtPoint(e.getPoint());
                        // Näita kogu rida
                        StringBuilder rowData = new StringBuilder();
                        for (int i = 0; i < table.getColumnCount(); i++) {
                            rowData.append(table.getValueAt(row, i)).append(" | ");
                        }
                        JOptionPane.showMessageDialog(table, "Valitud rida: \n" + rowData);
                    }
                }
            });

            //  Tabeli päis rasvaseks
            JTableHeader header = table.getTableHeader();
            Font headerFont = header.getFont().deriveFont(Font.BOLD);
            header.setFont(headerFont);

            int[] columnWidths = {100, 120, 80, 90, 150};
            for (int i = 0; i < columnWidths.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }
            // Joondame alates teisest veerust paremale serva
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            for (int i = 0; i < model.getColumnNames().length; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            }
            dlgScoreBoard = new ScoreBoardDialog(view);
            JScrollPane scrollPane = new JScrollPane(table);
            dlgScoreBoard.add(scrollPane);
            dlgScoreBoard.setTitle("Edetabel Andmebaas");
            return true;
        }
        return false;
    }

    private boolean createTable(ArrayList<ScoreData> result) {
        if (!result.isEmpty()) { // Ei ole tühi
            Collections.sort(result);
            // Loome kahemõõtmelise stirngide massiivi
            String[][] data = new String[result.size()][5]; // Veergude arv on meil tabelis 5
            for (int i = 0; i < result.size(); i++) {
                data[i][0] = result.get(i).getName();
                data[i][1] = result.get(i).formatGameTime(result.get(i).getTime());
                data[i][2] = String.valueOf(result.get(i).getClicks());
                data[i][3] = String.valueOf(result.get(i).getBoard());
                data[i][4] = result.get(i).getPlayedTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            }
            JTable table = new JTable(data, model.getColumnNames()); // columnNames on massiiv

            // Määrame veergude laiused
            int[] columnWidth = {100, 80, 60, 80, 160};  // Veergude laiused ekraanil
            for (int i = 0; i < columnWidth.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);

            }
            // Loo edetabeliaken kerimisribaga
            dlgScoreBoard = new ScoreBoardDialog(view);
            dlgScoreBoard.add(new JScrollPane(table));
            dlgScoreBoard.setTitle("Edetabel failist");
            return true;
        }
        return false; // Failist loetud info
    }

    private void setupDlgScoreBoard() {
        dlgScoreBoard.setModal(true); // Olemasoleva akna peal, peab kinni klikkima
        dlgScoreBoard.pack();
        dlgScoreBoard.setLocationRelativeTo(null); // Paigutame keset ekraani
        dlgScoreBoard.setVisible(true);
    }


} // Lõpp
