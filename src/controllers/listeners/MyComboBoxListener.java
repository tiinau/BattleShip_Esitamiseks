package controllers.listeners;

import models.Model;
import views.View;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyComboBoxListener implements ItemListener {
    private Model model;
    private View view;


    public MyComboBoxListener(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Mängualau suuruse muutmine, siis
     * mängulaua joonistamine (vasak pool) uuesti
     * @param e the event to be processed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        //System.out.println(e.getItem()); //Test
        if(e.getStateChange() == ItemEvent.SELECTED) {
            // TEST System.out.println(e.getItem());
            String number = e.getItem().toString(); // Teeb väärtusestringiks
            int size = Integer.parseInt(number);    // Tee eelnev string täisarvuks
            view.getLblGameBoard().setText(String.valueOf(size + " x " + size));
            model.setBoardSize(size);           // Määrab mäbgulaua suuruse
            view.pack();                        // Et suurus muutuks
            view.repaint();                     // Joonista uuesti
        }
    }
}