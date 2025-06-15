package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import vu.utms.model.TransportOfficer;
import vu.utms.model.User; 

/**
 *
 * @author phill
 */
public class MainUI extends JFrame {
    private final User currentUser;

    public MainUI(User user) {
        super("VU UTMS — Welcome " + user.getUsername());
        this.currentUser = user;

        // Frame settings
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top bar with welcome label and logout button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblWelcome = new JLabel("Welcome, " + currentUser.getUsername());
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.PLAIN, 16f));
        topBar.add(lblWelcome, BorderLayout.WEST);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
        });
        topBar.add(btnLogout, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Tabbed pane for modules
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Home",    new HomeUI());
        tabs.addTab("Routes",  new RouteUI(currentUser));

        // Only Transport Officers get access to manage data
        if (currentUser instanceof TransportOfficer) {
            tabs.addTab("Drivers",  new DriverUI());
            tabs.addTab("Vehicles", new VehicleUI());
            tabs.addTab("Users",    new UserUI());
        }

        add(tabs, BorderLayout.CENTER);
    }
}