package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import vu.utms.dao.UserDAO;
import vu.utms.model.User;

/**
 *
 * @author phill
 */
public class LoginUI extends JFrame {
    private final JTextField txtUser    = new JTextField(15);
    private final JPasswordField txtPwd = new JPasswordField(15);
    private final JButton btnLogin      = new JButton("Login");
    private final UserDAO userDAO       = new UserDAO();

    public LoginUI() {
        super("VU UTMS — Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel p = new JPanel(new GridLayout(3,2,5,5));
        p.setBorder(new EmptyBorder(20,20,20,20));
        p.add(new JLabel("Username:"));
        p.add(txtUser);
        p.add(new JLabel("Password:"));
        p.add(txtPwd);
        p.add(new JLabel());
        p.add(btnLogin);
        add(p, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> doLogin());
        getRootPane().setDefaultButton(btnLogin);

        pack();
        setLocationRelativeTo(null);
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPwd.getPassword());
        User user = userDAO.authenticate(u, p);
        if (user == null) {
            JOptionPane.showMessageDialog(this,
                "Invalid credentials", "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        } else {
            SwingUtilities.invokeLater(() -> {
                new MainUI(user).setVisible(true);
            });
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
