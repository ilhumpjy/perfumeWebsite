package controller;

import java.sql.*;

public class UserBean {
    private String username;
    private String password;
    private int userId; // ✅ Tambah field userId

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() { // ✅ Getter untuk userId
        return userId;
    }

    // Validation method using Derby DB
    public boolean validate() {
        boolean status = false;

        try {
            // Load Derby JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // DB connection
            Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // SQL Query
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM Users WHERE user_username=? AND user_password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id"); // ✅ Simpan user_id
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
