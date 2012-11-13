package gui;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/12/12
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaPanel extends JPanel {

    public FormulaPanel(String formula) {
        Box b1 = Box.createVerticalBox();
        Box b2 = Box.createHorizontalBox();
        TeXFormula tf = new TeXFormula(formula);
        TeXIcon icon = tf.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
        icon.setInsets(new Insets(5, 5, 5, 5));

        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
        flabel = new JLabel();
        flabel.setIcon(icon);

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));

        JButton eval = new JButton("EVAL");
        JButton reset = new JButton("RESET");

        buttons.add(eval);
        buttons.add(reset);

        b2.add(flabel);
        b2.add(Box.createHorizontalGlue());
        b1.add(b2);
        b1.add(buttons);
        add(b1);
    }

    private JLabel flabel;
    private JPanel buttons;
}

