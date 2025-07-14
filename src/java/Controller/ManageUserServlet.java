package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ManageUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Map<String, Object>> users = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();
                user.put("id", rs.getInt("user_id"));
                user.put("name", rs.getString("user_name"));
                user.put("username", rs.getString("user_username"));
                user.put("email", rs.getString("user_email"));
                user.put("password", rs.getString("user_password"));
                users.add(user);
            }

            conn.close();
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("users", users);
        RequestDispatcher rd = request.getRequestDispatcher("manage_users.jsp");
        rd.forward(request, response);
    }
}
