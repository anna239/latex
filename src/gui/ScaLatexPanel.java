package gui;

import javax.swing.*;
import java.awt.*;

public class ScaLatexPanel extends JPanel {
    private static final Insets INSETS = new Insets(5, 5, 5, 5);

    public ScaLatexPanel(String[] formulae) {
        setLayout(new GridBagLayout());

        int cnt = 0;
        for (String aFormulae : formulae) {
            try {
                add(new SingleFormulaPanel(aFormulae), new GridBagConstraints(0, cnt, 1, 1, 1, 0,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, INSETS, 0, 0));
                ++cnt;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if (cnt == 0) {
            removeAll();
            setLayout(new GridLayout(1, 1));
            JLabel label = new JLabel("No parseable formulae found.", SwingConstants.CENTER);
            add(label);
        } else {
            add(new JLabel(), new GridBagConstraints(0, cnt, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.BOTH, INSETS, 0, 0));
        }
    }
}
