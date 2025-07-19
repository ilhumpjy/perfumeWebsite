package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserBean {
    private String user_name;
    private String user_username;
    private String user_email;
    private String user_password;
    private int user_id; // ✅ Tambah user_id untuk simpan id dari database

    // Constructors
    public UserBean(String user_name, String user_username, String user_email, String user_password) {
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public UserBean() {
        this.user_name = "";
        this.user_username = "";
        this.user_email = "";
        this.user_password = "";
    }

    // Getters & Setters
    public String getUser_name() { return user_name; }
    public String getUser_username() { return user_username; }
    public String getUser_email() { return user_email; }
    public String getUser_password() { return user_password; }
    public int getUser_id() { return user_id; }

    public void setUser_name(String user_name) { this.user_name = user_name; }
    public void setUser_username(String user_username) { this.user_username = user_username; }
    public void setUser_email(String user_email) { this.user_email = user_email; }
    public void setUser_password(String user_password) { this.user_password = user_password; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    // Validate method untuk login
    public boolean validate() {
        boolean status = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load Derby JDBC Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // DB connection
            con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // SQL Query
            String sql = "SELECT * FROM Users WHERE user_username=? AND user_password=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, this.user_username);
            ps.setString(2, this.user_password);

            rs = ps.executeQuery();
            if (rs.next()) {
                // Dapatkan user_id dari database
                this.user_id = rs.getInt("user_id");
                this.user_name = rs.getString("user_name");
                this.user_email = rs.getString("user_email");
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return status;
    }
}
