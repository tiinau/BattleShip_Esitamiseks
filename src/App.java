import controllers.Controller;
import models.Model;
import views.View;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Model model = new Model();
                View view = new View(model);
                Controller controller = new Controller(model, view);

                //Hiire liikumise aktiviseerimiseks
                view.registerGameBoardMouse(controller);

                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sellega paneme frame tööle (frame on view-s tehtud)
                view.pack();
                view.setLocationRelativeTo(null); // Paneb ekraani keskele
                view.setVisible(true); // Tee Jframe nähtavaks /
            }
        });
    }
}
