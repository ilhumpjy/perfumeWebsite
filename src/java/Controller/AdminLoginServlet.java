package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValid = false;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "SELECT * FROM ADMIN WHERE ADMIN_USERNAME = ? AND ADMIN_PASSWORD = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                isValid = true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isValid) {
            // ✅ Simpan dalam session
            HttpSession session = request.getSession();
            session.setAttribute("admin_username", username);

            // ✅ Redirect ke admin dashboard
            response.sendRedirect("admin.jsp");
        } else {
            // ❌ Login gagal
            response.sendRedirect("adminError.jsp");
        }
    }
}
