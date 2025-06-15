package vu.utms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vu.utms.model.Driver;
import vu.utms.util.DatabaseConnection;

/**
 *
 * @author phill
 */
public class DriverDAO {

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT id, driverName, licenseNumber FROM Driver";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                drivers.add(new Driver(
                    rs.getInt("id"),
                    rs.getString("driverName"),
                    rs.getString("licenseNumber")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading drivers: " + e.getMessage());
        }
        return drivers;
    }

    public Driver getDriverById(int driverId) {
        String sql = "SELECT id, driverName, licenseNumber FROM Driver WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Driver(
                        rs.getInt("id"),
                        rs.getString("driverName"),
                        rs.getString("licenseNumber")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading driver " + driverId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addDriver(Driver d) {
        String sql = "INSERT INTO Driver (id, driverName, licenseNumber) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getLicenseNumber());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error adding driver: " + e.getMessage());
            return false;
        }
    }
    
    /** Update an existing driver’s driverName or license.
     * @param d
     * @return  */
    public boolean updateDriver(Driver d) {
        String sql = "UPDATE Driver SET driverName = ?, licenseNumber = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setString(2, d.getLicenseNumber());
            ps.setInt(3, d.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error updating driver: " + e.getMessage());
            return false;
        }
    }

    /** Delete a driver by its ID.
     * @param driverId
     * @return  */
    public boolean deleteDriver(int driverId) {
        String sql = "DELETE FROM Driver WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting driver: " + e.getMessage());
            return false;
        }
    }
}