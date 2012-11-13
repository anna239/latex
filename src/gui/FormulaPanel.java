package gui;

import latex.calculator.TeXCalculator;
import latex.parser.ExpressionParser;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/12/12
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaPanel extends JPanel {

    public FormulaPanel(final String formula) {
        this.formula = formula;
        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createHorizontalBox();

        flabel = new JLabel();

        setFormula(generateIcon(formula));

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));

        JButton eval = new JButton("EVAL");
        eval.addActionListener(new evalAction());
        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFormula(generateIcon(formula));
            }
        });

        buttons.add(eval);
        buttons.add(reset);

        b2.add(flabel);
        b2.add(Box.createHorizontalGlue());
        b1.add(b2);
        b1.add(buttons);
        add(b1);
    }

    private void setFormula(TeXIcon icon) {
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
        flabel.setIcon(icon);
    }


    private TeXIcon generateIcon(String formula) {
        TeXFormula tf = new TeXFormula(formula);
        TeXIcon icon = tf.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
        icon.setInsets(new Insets(5, 5, 5, 5));
        return icon;
    }
    class evalAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(evalDialog == null) {
                evalDialog = new evalPanel();
            }
            if(evalDialog.showDialog(FormulaPanel.this, "Arguments Input")) {
                String s = evalDialog.getArgs();
                if (s == null) {
                    s = "" ;
                }
                String[] tokens = s.split(";");
                String[] varnames = new String[tokens.length];
                double[] values = new double[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    String[] p = tokens[i].split("=");
                    if(p == null || p.length < 2) continue;
                    varnames[i] = p[0];
                    values[i] = Double.parseDouble(p[1]);
                }
                TeXCalculator calc = new TeXCalculator();
                calc.setContext(varnames, values);
                double res = calc.calculate(new ExpressionParser().parse(formula));
                setFormula(generateIcon(formula + " = " + res));
            }
        }
    }

    private evalPanel evalDialog = null;
    private JLabel flabel;
    private JPanel buttons;
    final private String formula;
}
