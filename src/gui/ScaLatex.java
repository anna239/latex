package gui;

import latex.formulaextractor.FormulaExtractor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.showOpenDialog(this);
        String filename = chooser.getSelectedFile().getAbsolutePath();

        formulae = new FormulaExtractor().extract(new File(filename));
        ScaLatexPanel panel = new ScaLatexPanel(formulae);
        Container contentPane = getContentPane();

        JScrollPane pane = new JScrollPane(panel);

        contentPane.add(pane);

        pack();
    }

    private String[] formulae;
}








