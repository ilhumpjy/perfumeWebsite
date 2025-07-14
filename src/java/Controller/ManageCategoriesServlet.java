package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ManageCategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Map<String, Object>> categories = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Category");

            while (rs.next()) {
                Map<String, Object> cat = new HashMap<>();
                cat.put("id", rs.getInt("category_id"));
                cat.put("name", rs.getString("category_name"));
                categories.add(cat);
            }

            conn.close();
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("categories", categories);
        RequestDispatcher rd = request.getRequestDispatcher("manage_categories.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String newCategory = request.getParameter("category_name");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO Category (category_name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newCategory);
            stmt.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ManageCategoriesServlet");
    }
}