package views.dialogs;
import views.View;
import javax.swing.*;
import java.awt.*;

public class ScoreBoardDialog extends JDialog {
    public ScoreBoardDialog(View view ) {
        super(view, "Edetabel");
        setPreferredSize(new Dimension(500, 200)); // Dialoogi akna suurus
    }
}
