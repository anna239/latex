package gui;


import latex.formulaextractor.FormulaExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Evgeniy
 * Date: 14.11.12
 * Time: 21:18
 */
public class MainWindow extends JFrame {
    JComponent internal = new JLabel("");
    private volatile JScrollPane formulaPane = null;

    ExecutorService executor = Executors.newCachedThreadPool();

    private synchronized void setFileDropInternal(String msg) {
        formulaPane = null;
        remove(internal);

        internal = new JLabel(msg + "Drop LaTeX file here", SwingConstants.CENTER);
        internal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createDashedBorder(Color.GRAY, 6, 2)
        ));

        add(internal);
        pack();
    }

    private synchronized void setFileDropInternal() {
        setFileDropInternal("");
    }

    private synchronized void setFormulaContent(JScrollPane formulaContent) {
        remove(internal);
        internal = formulaContent;
        add(internal);
        pack();
    }

    private synchronized void loadTexFile(final String fileName) {
        formulaPane = null;
        setFileDropInternal();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int dots = 0;
                while (true) {
                    synchronized (MainWindow.this) {
                        if (formulaPane != null) break;
                        String text = "Loading";
                        int i = 0;
                        for (; i < dots; ++i) text += ".";
                        for (; i < 3; ++i) text += " ";
                        ((JLabel) internal).setText(text);
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {/*do nothing*/}
                    MainWindow.this.repaint();
                    dots = (dots + 1) % 4;
                }
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JScrollPane pane = new JScrollPane(new ScaLatexPanel(new FormulaExtractor().extract(new File(fileName))));
                    synchronized (MainWindow.this) {
                        formulaPane = pane;
                        setFormulaContent(formulaPane);
                    }
                } catch (Exception e) {
                    setFileDropInternal("File can't be loaded :-( ");
                }
            }
        });
    }

    private MainWindow() {
        setTitle("ScaLaTeX");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(1, 1));

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem openLatexFile = new MenuItem("Open LaTeX file");
        MenuItem closeLatexFile = new MenuItem("Close LaTeX file");
        MenuItem closeProgram = new MenuItem("Close ScaLaTeX");

        menu.add(openLatexFile);
        menu.add(closeLatexFile);
        menu.add(closeProgram);

        menuBar.add(menu);
        setMenuBar(menuBar);
        setFileDropInternal();

        openLatexFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.showOpenDialog(MainWindow.this);
                if (chooser.getSelectedFile() == null) return;
                String filename = chooser.getSelectedFile().getAbsolutePath();
                loadTexFile(filename);
            }
        });

        closeLatexFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFileDropInternal();
            }
        });

        closeProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setTransferHandler(new FileDropHandler());

        setMinimumSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
    }


    class FileDropHandler extends TransferHandler {
        public boolean canImport(TransferSupport supp) {
            return supp.isDrop() && supp.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        public boolean importData(TransferSupport support) {
            if (!canImport(support)) return false;

            try {
                Object data = support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                java.util.List fileList = (java.util.List) data;
                for (Object file : fileList) {
                    if (file instanceof File) {
                        loadTexFile(((File) file).getAbsolutePath());
                        break;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

    }


    public static void main(String[] args) {
        new MainWindow();
    }
}
