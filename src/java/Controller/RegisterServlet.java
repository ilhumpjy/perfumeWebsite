package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String name = request.getParameter("user_name");
        String username = request.getParameter("user_username");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO Users (user_name, user_username, user_email, user_password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, username); // andaikan user_username = username (atau boleh buat input field asing)
            stmt.setString(3, email);
            stmt.setString(4, password);

            stmt.executeUpdate();

            response.sendRedirect("UserLogin.jsp");

        } catch (SQLIntegrityConstraintViolationException e) {
            // Email already used
            request.setAttribute("errorMsg", "Email has already been used.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            // General error
            e.printStackTrace();
            request.setAttribute("errorMsg", "Registration failed. Please try again.");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        }
    }
}
