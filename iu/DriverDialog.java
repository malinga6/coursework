package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import vu.utms.model.Driver;

/**
 *
 * @author phill
 */
public class DriverDialog extends JDialog {
    private final JTextField txtId    = new JTextField(5);
    private final JTextField txtName  = new JTextField(15);
    private final JTextField txtLic   = new JTextField(15);
    private boolean confirmed = false;

    public DriverDialog(Driver d) {
        setModal(true);
        setTitle(d == null ? "Add Driver" : "Edit Driver");
        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.setBorder(new EmptyBorder(20,20,20,20));
        p.add(new JLabel("ID:"));    p.add(txtId);
        p.add(new JLabel("Name:"));  p.add(txtName);
        p.add(new JLabel("License:")); p.add(txtLic);
        add(p, BorderLayout.CENTER);

        JButton ok = new JButton("OK"), cancel = new JButton("Cancel");
        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);
        add(bp, BorderLayout.SOUTH);

        if (d != null) {
            txtId.setText(String.valueOf(d.getId()));
            txtId.setEnabled(false);
            txtName.setText(d.getName());
            txtLic.setText(d.getLicenseNumber());
        }

        ok.addActionListener(e -> { confirmed = true; dispose(); });
        cancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    public boolean showDialog(Component parent) {
        setLocationRelativeTo(parent);
        setVisible(true);
        return confirmed;
    }

    public Driver getDriver() {
        int id = Integer.parseInt(txtId.getText().trim());
        String name = txtName.getText().trim();
        String lic  = txtLic.getText().trim();
        return new Driver(id, name, lic);
    }
}