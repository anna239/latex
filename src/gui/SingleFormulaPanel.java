package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.GuiUtils.INSETS;
import static gui.GuiUtils.generateIcon;

/**
 * Created with IntelliJ IDEA.
 * User: Evgeniy
 * Date: 14.11.12
 * Time: 23:08
 */
public class SingleFormulaPanel extends JPanel {

    private JFrame evaluationFrame = null;

    public SingleFormulaPanel(final String formula) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3),
                BorderFactory.createDashedBorder(Color.LIGHT_GRAY)
        ));

        JLabel formulaLabel = new JLabel();
        formulaLabel.setIcon(generateIcon(formula));

        JButton eval = new JButton("Evaluate");
        JButton delete = new JButton("Remove formula");

        add(formulaLabel, new GridBagConstraints(0, 0, 1, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, INSETS, 0, 0));
        add(eval, new GridBagConstraints(1, 0, 1, 1, 0, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, INSETS, 0, 0));
        add(delete, new GridBagConstraints(1, 1, 1, 1, 0, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, INSETS, 0, 0));

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                parent.remove(SingleFormulaPanel.this);
                parent.doLayout();
                parent.repaint();
            }
        });

        eval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationFrame == null) evaluationFrame = new EvaluationFrame(SingleFormulaPanel.this, formula);
                evaluationFrame.requestFocus();
            }
        });
    }


    public void resetEvaluationFrame() {
        evaluationFrame = null;
    }
}
