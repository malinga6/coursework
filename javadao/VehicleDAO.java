package vu.utms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vu.utms.model.Vehicle;
import vu.utms.util.DatabaseConnection;

/**
 *
 * @author phill
 */
public class VehicleDAO {

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT id, plateNumber, capacity, type FROM Vehicle";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(new Vehicle(
                    rs.getInt("id"),
                    rs.getString("plateNumber"),
                    rs.getInt("capacity"),
                    Vehicle.Type.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
        }
        return vehicles;
    }

    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT id, plateNumber, capacity, type FROM Vehicle WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("plateNumber"),
                        rs.getInt("capacity"),
                        Vehicle.Type.valueOf(rs.getString("type"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading vehicle " + vehicleId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addVehicle(Vehicle v) {
        String sql = "INSERT INTO Vehicle (id, plateNumber, capacity, type) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getId());
            ps.setString(2, v.getPlateNumber());
            ps.setInt(3, v.getCapacity());
            ps.setString(4, v.getType().name());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }
    
    /** Update an existing vehicle’s details.
     * @param v
     * @return  */
    public boolean updateVehicle(Vehicle v) {
        String sql = "UPDATE Vehicle SET plateNumber = ?, capacity = ?, type = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getPlateNumber());
            ps.setInt(2, v.getCapacity());
            ps.setString(3, v.getType().name());
            ps.setInt(4, v.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    /** Delete a vehicle by its ID.
     * @param vehicleId
     * @return  */
    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM Vehicle WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }

    /**
     * If you have an 'assignedDriverId' column, you can update it:
     * @param vehicleId
     * @param driverId
     * @return 
     */
    public boolean assignDriver(int vehicleId, int driverId) {
        String sql = "UPDATE Vehicle SET assignedDriverId = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, driverId);
            ps.setInt(2, vehicleId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error assigning driver: " + e.getMessage());
            return false;
        }
    }
}
