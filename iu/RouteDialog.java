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
import vu.utms.model.Route;

/**
 *
 * @author phill
 */
public class RouteDialog extends JDialog {
    private final JTextField txtId   = new JTextField(5);
    private final JTextField txtName = new JTextField(15);
    private final JTextField txtOrig = new JTextField(10);
    private final JTextField txtDest = new JTextField(10);
    private final JTextField txtTime = new JTextField(5);
    private boolean confirmed = false;

    public RouteDialog(Route r) {
        setModal(true);
        setTitle(r == null ? "Add Route" : "Edit Route");
        JPanel p = new JPanel(new GridLayout(5,2,5,5));
        p.setBorder(new EmptyBorder(20,20,20,20));
        p.add(new JLabel("ID:"));          p.add(txtId);
        p.add(new JLabel("Name:"));        p.add(txtName);
        p.add(new JLabel("Origin:"));      p.add(txtOrig);
        p.add(new JLabel("Destination:")); p.add(txtDest);
        p.add(new JLabel("Time:"));        p.add(txtTime);
        add(p, BorderLayout.CENTER);

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        JPanel b = new JPanel();
        b.add(btnOk); b.add(btnCancel);
        add(b, BorderLayout.SOUTH);

        if (r!=null) {
            txtId.setText(String.valueOf(r.getId()));
            txtId.setEnabled(false);
            txtName.setText(r.getName());
            txtOrig.setText(r.getOrigin());
            txtDest.setText(r.getDestination());
            txtTime.setText(r.getTime());
        }

        btnOk.addActionListener(e->{
            confirmed = true;
            dispose();
        });
        btnCancel.addActionListener(e-> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    public boolean showDialog(Component parent) {
        setLocationRelativeTo(parent);
        setVisible(true);
        return confirmed;
    }

    public Route getRoute() {
        return new Route(
            Integer.parseInt(txtId.getText().trim()),
            txtName.getText().trim(),
            txtOrig.getText().trim(),
            txtDest.getText().trim(),
            txtTime.getText().trim()
        );
    }
}