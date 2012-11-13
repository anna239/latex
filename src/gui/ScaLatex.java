package gui;

import latex.formulaextractor.FormulaExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 11/12/12
 * Time: 8:34 PM
 */
public class ScaLatex {
    public static void main(String[] args) {
        ScaLatexFrame frame = new ScaLatexFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class ScaLatexFrame extends JFrame {
    public ScaLatexFrame() {

        setTitle("ScaLaTex");

        JMenuBar bar = new JMenuBar();

        JMenu menu = new JMenu("File");

        JMenuItem item = new JMenuItem("Open TeX file");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.showOpenDialog(ScaLatexFrame.this);
                if (chooser.getSelectedFile() == null) return;
                String filename = chooser.getSelectedFile().getAbsolutePath();
                addContent(filename);
            }
        });

        setTransferHandler(new FileDropHandler());

        setJMenuBar(bar);
        bar.add(menu);
        menu.add(item);
        setMinimumSize(new Dimension(400, 600));

        setLocationRelativeTo(null);
    }

    JScrollPane pane = null;

    private void addContent(String filename) {
        if(pane != null){
            remove(pane);
            pack();
        }

        String[] formulae = new FormulaExtractor().extract(new File(filename));
//        String[] formulae = new String[20];
//        for (int i = 0; i < formulae.length; i++) formulae[i] = "a + b = ";

        pane = new JScrollPane(new ScaLatexPanel(formulae));

        add(pane);

        pack();
    }

    class FileDropHandler extends TransferHandler {
        public boolean canImport(TransferSupport supp) {
            return supp.isDrop() && supp.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        public boolean importData(TransferSupport supp) {
            if (!canImport(supp)) {
                return false;
            }
            Transferable t = supp.getTransferable();
            try {
                Object data = t.getTransferData(DataFlavor.javaFileListFlavor);
                java.util.List fileList = (java.util.List) data;
                for (Object file : fileList) {
                    if (file instanceof File) {
                        addContent(((File) file).getAbsolutePath());
                    }
                }
            } catch (UnsupportedFlavorException e) {
                return false;
            } catch (IOException e) {
                return false;
            }
            return true;
        }
    }
}








