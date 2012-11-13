package gui;

import javax.swing.*;
import java.awt.*;

public class ScaLatexPanel extends JPanel {
    public ScaLatexPanel(String[] formulae) {
        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        add(panel);

        for (String aFormulae : formulae) {
            try {
                panel.add(new FormulaPanel(aFormulae));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
