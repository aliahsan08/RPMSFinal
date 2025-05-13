package oopproject.rpmsfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // JDBC connection parameters
    private static final String URL  = "jdbc:mysql://127.0.0.1:3306/rpms?user=root";
    private static final String USER = "root";
    private static final String PASS = "qwerty99+";

    static {
        try {
            // Load the MySQL driver (optional for Connector/J 8.x+, as it auto-registers)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens and returns a new Connection.
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
