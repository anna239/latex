package gui;

import javax.swing.*;
import java.awt.*;

public class ScaLatexPanel extends JPanel {
    public ScaLatexPanel(String[] formulae) {
        setLayout(new GridLayout(formulae.length, 1));
        for (int i = 0; i < formulae.length; i++) {
            try {
                add(new FormulaPanel(formulae[i]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
