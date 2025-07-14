package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/PerfumeDB";
    private static final String JDBC_USERNAME = "app"; // <-- ganti dengan username MySQL kau
    private static final String JDBC_PASSWORD = "app";     // <-- kalau tiada password, biar kosong

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // <-- betul untuk MySQL Connector 8.x
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }
}
