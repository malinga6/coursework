package vu.utms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vu.utms.model.Route;
import vu.utms.util.DatabaseConnection;

/**
 *
 * @author phill
 */
public class RouteDAO {

    /**
     * Fetches all routes from the Access database.
     * @return 
     */
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT id, routeName, origin, destination, time FROM Route";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                routes.add(new Route(
                    rs.getInt("id"),
                    rs.getString("routeName"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getString("time")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading routes: " + e.getMessage());
        }
        return routes;
    }

    public Route getRouteById(int routeId) {
        String sql = "SELECT id, routeName, origin, destination, time FROM Route WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Route(
                        rs.getInt("id"),
                        rs.getString("routeName"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getString("time")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading route " + routeId + ": " + e.getMessage());
        }
        return null;
    }

    public boolean addRoute(Route r) {
        String sql = "INSERT INTO Route (id, routeName, origin, destination, time) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getId());
            ps.setString(2, r.getName());
            ps.setString(3, r.getOrigin());
            ps.setString(4, r.getDestination());
            ps.setString(5, r.getTime());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Error adding route: " + e.getMessage());
            return false;
        }
    }

    /** Update an existing route’s details. */
    public boolean updateRoute(Route r) {
        String sql = "UPDATE Route SET routeName = ?, origin = ?, destination = ?, time = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getOrigin());
            ps.setString(3, r.getDestination());
            ps.setString(4, r.getTime());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Error updating route: " + e.getMessage());
            return false;
        }
    }

    /** Delete a route by its ID. */
    public boolean deleteRoute(int routeId) {
        String sql = "DELETE FROM Route WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Error deleting route: " + e.getMessage());
            return false;
        }
    }
}