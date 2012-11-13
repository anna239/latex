package gui;

import latex.formulaextractor.FormulaExtractor;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/12/12
 * Time: 8:34 PM
 */
public class ScaLatex {
    public static void main(String[] args)
    {
        ScaLatexFrame frame = new ScaLatexFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ScaLatexFrame extends JFrame
{
    public ScaLatexFrame()
    {
        setTitle("ScaLaTex");
        formulae = new String[]{"\\frac {V_m} {K_M+S} = ", "a ^ 2 + b ^ 2 = ", "\\sqrt[3]{x+1} =  ", "\\int_{0}^{3} f(x) dx ="};
        ScaLatexPanel panel = new ScaLatexPanel(formulae);
        Container contentPane = getContentPane();
        contentPane.add(panel);
        pack();
    }

    private String[] formulae;
}







