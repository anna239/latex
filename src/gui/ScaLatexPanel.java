package gui;

import javax.swing.*;
import java.awt.*;

public class ScaLatexPanel extends JPanel {
    public ScaLatexPanel(String[] formulae) {
        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        JScrollPane pane = new JScrollPane(panel);
        add(pane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 600));

        for (int i = 0; i < formulae.length; i++) {
            try {
                panel.add(new FormulaPanel(formulae[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
