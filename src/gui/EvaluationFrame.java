package gui;

import latex.calculator.TeXCalculator;
import latex.formulaextractor.FormulaExtractor;
import latex.parser.ExpressionParser;
import latex.structure.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import static gui.GuiUtils.INSETS;

/**
 * Created with IntelliJ IDEA.
 * User: Evgeniy
 * Date: 14.11.12
 * Time: 23:47
 */
public class EvaluationFrame extends JFrame {
    private JLabel formulaLabel = new JLabel();
    private String formula;
    private Node formulaAST;

    private String[] names;
    private double[] values;

    private TeXCalculator calc = new TeXCalculator();

    public EvaluationFrame(final SingleFormulaPanel parent, final String formula) {
        this.formula = formula;
        formulaAST = GuiUtils.PARSER_FOR_GUI.parse(formula);
        setTitle("Formula evaluation");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new GridBagLayout());

        formulaLabel.setIcon(GuiUtils.generateIcon(formula));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parent.resetEvaluationFrame();
                dispose();
            }
        });

        add(formulaLabel, new GridBagConstraints(0, 0, 2, 1, 1, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(20, 50, 20, 50), 0, 0));

        names = calc.valsRequiredAsArray(formulaAST);
        values = new double[names.length];

        for (int i = 0; i < names.length; ++i) {
            final JLabel variableLabel = new JLabel();
            variableLabel.setIcon(GuiUtils.generateIcon(names[i] + " ="));

            final JTextField valueText = new JTextField();
            valueText.setText(String.valueOf(values[i] = calc.getVal(names[i])));
            calc.setVal(names[i], calc.getVal(names[i]));

            add(variableLabel, new GridBagConstraints(0, i + 1, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GuiUtils.INSETS, 0, 0));
            add(valueText, new GridBagConstraints(1, i + 1, 1, 1, 0, 0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, GuiUtils.INSETS, 0, 0));

            final int _i = i;
            valueText.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        values[_i] = Double.parseDouble(valueText.getText());
                        updateResult();
                    } catch (Exception ex) {
                    }
                }
            });

        }

        updateResult();

        setMinimumSize(new Dimension(400, 50));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void updateResult() {
        try {
            calc.setContext(names, values);
            formulaLabel.setIcon(GuiUtils.generateIcon(formula + " = " + calc.calculate(formulaAST)));
        } catch (Exception e) {
            formulaLabel.setIcon(GuiUtils.generateIcon(formula + " = ?"));
        }
    }
}
