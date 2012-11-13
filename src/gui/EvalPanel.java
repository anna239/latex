package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: stels
 * Date: 13.11.12
 * Time: 15:18
 */
public class EvalPanel extends JPanel {
    private JTextField args;
    private JButton okButton;
    private JDialog dialog;
    private boolean ok;

    public EvalPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel(" Args: "), new GridBagConstraints());
        panel.add(args = new JTextField(""));

        add(panel);
        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ok = true;
                dialog.setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dialog.setVisible(false);
            }
        });

        // add buttons to southern border

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    public boolean showDialog(Component parent, String title) {
        ok = false;

        // locate the owner frame

        Frame owner = null;
        if (parent instanceof Frame)
            owner = (Frame) parent;
        else
            owner = (Frame) SwingUtilities.getAncestorOfClass(
                    Frame.class, parent);

        // if first time, or if owner has changed, make new dialog

        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            dialog.getContentPane().add(this);
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
        }

        // set title and show dialog

        dialog.setTitle(title);
        dialog.setVisible(true);
        return ok;
    }

    public String getArgs() {
        return args.getText();
    }
}
