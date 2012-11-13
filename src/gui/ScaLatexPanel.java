package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/12/12
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScaLatexPanel extends JPanel {
    public ScaLatexPanel(String[] formulae) {
        setLayout(new GridLayout(formulae.length, 1));
        for (int i = 0; i < formulae.length; i++) {
             add(new FormulaPanel(formulae[i]));
        }
    }
}
