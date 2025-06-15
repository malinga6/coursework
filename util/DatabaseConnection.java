package vu.utms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author phill
 */
public class DatabaseConnection {
    // Use a relative path so it works across machines:
    private static final String DB_PATH = "UTMS.accdb";
    private static final String URL =
        "jdbc:ucanaccess://" + DB_PATH;

    /**
     * Open and return a Connection to the Access database.
     * @return 
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws SQLException {
        // No need to Class.forName(); UCanAccess auto-loads its driver
        return DriverManager.getConnection(URL);
    }
}
