package vu.utms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import vu.utms.model.Lecturer;
import vu.utms.model.Student;
import vu.utms.model.TransportOfficer;
import vu.utms.model.User;
import vu.utms.util.DatabaseConnection;

/**
 *
 * @author phill
 */
public class UserDAO {

    /**
     * Returns a User subclass instance if credentials match, or null.Expects User table with columns:
   id, username, password, role, matricNumber, staffId
     * @param username
     * @param password
     * @return 
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT id, role, matricNumber, staffId "
                   + "FROM [User] WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String role = rs.getString("role");
                    return switch (role.toLowerCase()) {
                        case "student" -> new Student(
                                id, username, password,
                                rs.getString("matricNumber")
                        );
                        case "lecturer" -> new Lecturer(
                                id, username, password,
                                rs.getString("staffId")
                        );
                        case "transportofficer", "officer" -> new TransportOfficer(id, username, password);
                        default -> null;
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role, matricNumber, staffId FROM [User]";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String role = rs.getString("role");
                User u;
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String pass = rs.getString("password");

                u = switch (role.toLowerCase()) {
                    case "student" -> new Student(id, user, pass, rs.getString("matricNumber"));
                    case "lecturer" -> new Lecturer(id, user, pass, rs.getString("staffId"));
                    default -> new TransportOfficer(id, user, pass);
                };
                users.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public User getUserById(int userId) {
        String sql = "SELECT username, password, role, matricNumber, staffId "
                   + "FROM [User] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");
                    return switch (role.toLowerCase()) {
                        case "student" -> new Student(userId, user, pass, rs.getString("matricNumber"));
                        case "lecturer" -> new Lecturer(userId, user, pass, rs.getString("staffId"));
                        default -> new TransportOfficer(userId, user, pass);
                    };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading user " + userId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addUser(User u) {
        String sql = "INSERT INTO [User] (id, username, password, role, matricNumber, staffId) "
                   + "VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, u.getId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());

            if (u instanceof Student student) {
                ps.setString(4, "student");
                ps.setString(5, student.getMatricNumber());
                ps.setNull(6, Types.VARCHAR);
            } else if (u instanceof Lecturer) {
                ps.setString(4, "lecturer");
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, ((Lecturer)u).getStaffId());
            } else {
                ps.setString(4, "transportofficer");
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
            }

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }
    
    /** Update a user’s credentials or role-specific fields.
     * @param u
     * @return  */
    public boolean updateUser(User u) {
        String sql = "UPDATE [User] SET username = ?, password = ?, role = ?, matricNumber = ?, staffId = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());

            if (u instanceof Student) {
                ps.setString(3, "student");
                ps.setString(4, ((Student)u).getMatricNumber());
                ps.setNull(5, Types.VARCHAR);
            } else if (u instanceof Lecturer) {
                ps.setString(3, "lecturer");
                ps.setNull(4, Types.VARCHAR);
                ps.setString(5, ((Lecturer)u).getStaffId());
            } else {
                ps.setString(3, "transportofficer");
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
            }
            ps.setInt(6, u.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    /** Delete a user by its ID.
     * @param userId
     * @return  */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM [User] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}