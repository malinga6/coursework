package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import vu.utms.model.Lecturer;
import vu.utms.model.Student;
import vu.utms.model.TransportOfficer;
import vu.utms.model.User;

/**
 *
 * @author phill
 */
public class UserDialog extends JDialog {
    private final JTextField txtId       = new JTextField(5);
    private final JTextField txtUser     = new JTextField(10);
    private final JPasswordField txtPass = new JPasswordField(10);
    private final JComboBox<String> cmbRole =
        new JComboBox<>(new String[]{"Student","Lecturer","TransportOfficer"});
    private final JTextField txtMatric   = new JTextField(10);
    private final JTextField txtStaff    = new JTextField(10);
    private boolean confirmed = false;

    public UserDialog(User u) {
        setModal(true);
        setTitle(u==null ? "Add User" : "Edit User");

        JPanel p = new JPanel(new GridLayout(6,2,5,5));
        p.setBorder(new EmptyBorder(20,20,20,20));
        p.add(new JLabel("ID:"));       p.add(txtId);
        p.add(new JLabel("Username:")); p.add(txtUser);
        p.add(new JLabel("Password:")); p.add(txtPass);
        p.add(new JLabel("Role:"));     p.add(cmbRole);
        p.add(new JLabel("Matric#:"));  p.add(txtMatric);
        p.add(new JLabel("Staff ID:")); p.add(txtStaff);
        add(p, BorderLayout.CENTER);

        JButton ok = new JButton("OK"), cancel = new JButton("Cancel");
        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);
        add(bp, BorderLayout.SOUTH);

        if (u != null) {
            txtId.setText(String.valueOf(u.getId()));
            txtId.setEnabled(false);
            txtUser.setText(u.getUsername());
            txtPass.setText(u.getPassword());
            String role = u.getClass().getSimpleName();
            cmbRole.setSelectedItem(role);
            if (u instanceof Student)
                txtMatric.setText(((Student)u).getMatricNumber());
            if (u instanceof Lecturer)
                txtStaff.setText(((Lecturer)u).getStaffId());
        }

        cmbRole.addActionListener(e -> updateFields());
        updateFields();

        ok.addActionListener(e -> { confirmed = true; dispose(); });
        cancel.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    private void updateFields() {
        String role = (String)cmbRole.getSelectedItem();
        txtMatric.setEnabled("Student".equals(role));
        txtStaff .setEnabled("Lecturer".equals(role));
    }

    public boolean showDialog(Component parent) {
        setLocationRelativeTo(parent);
        setVisible(true);
        return confirmed;
    }

    public User getUser() {
        int id = Integer.parseInt(txtId.getText().trim());
        String u   = txtUser.getText().trim();
        String p   = new String(txtPass.getPassword());
        String role= (String)cmbRole.getSelectedItem();

        return switch (role) {
            case "Student" -> new Student(id,u,p,txtMatric.getText().trim());
            case "Lecturer" -> new Lecturer(id,u,p,txtStaff.getText().trim());
            default -> new TransportOfficer(id,u,p);
        };
    }
}