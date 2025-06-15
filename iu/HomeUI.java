package vu.utms.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import vu.utms.dao.DriverDAO;
import vu.utms.dao.RouteDAO;
import vu.utms.dao.UserDAO;
import vu.utms.dao.VehicleDAO;
import vu.utms.model.Lecturer;
import vu.utms.model.Student;
import vu.utms.model.TransportOfficer;
import vu.utms.model.User;

/**
 *
 * @author phill
 */
public class HomeUI extends JPanel {
    private final RouteDAO routeDAO     = new RouteDAO();
    private final DriverDAO driverDAO   = new DriverDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final UserDAO userDAO       = new UserDAO();

    public HomeUI() {
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(10,10,10,10));

        // Title
        JLabel title = new JLabel("Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Grid of summary cards
        JPanel grid = new JPanel(new GridLayout(2, 3, 10, 10));
        grid.add(createCard("Routes", routeDAO.getAllRoutes().size()));
        grid.add(createCard("Drivers", driverDAO.getAllDrivers().size()));
        grid.add(createCard("Vehicles", vehicleDAO.getAllVehicles().size()));

        // Count users by subclass
        List<User> allUsers = userDAO.getAllUsers();
        int students = (int) allUsers.stream().filter(u -> u instanceof Student).count();
        int lecturers = (int) allUsers.stream().filter(u -> u instanceof Lecturer).count();
        int officers  = (int) allUsers.stream().filter(u -> u instanceof TransportOfficer).count();

        grid.add(createCard("Students", students));
        grid.add(createCard("Lecturers", lecturers));
        grid.add(createCard("Transport Officers", officers));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel createCard(String name, int count) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new CompoundBorder(
            new LineBorder(Color.DARK_GRAY), 
            new EmptyBorder(10,10,10,10))
        );
        JLabel lblName = new JLabel(name, SwingConstants.CENTER);
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel lblCount = new JLabel(String.valueOf(count), SwingConstants.CENTER);
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 32));
        card.add(lblName, BorderLayout.NORTH);
        card.add(lblCount, BorderLayout.CENTER);
        return card;
    }
}