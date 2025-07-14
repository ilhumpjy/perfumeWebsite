package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteCategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String categoryId = request.getParameter("category_id");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "DELETE FROM Category WHERE category_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(categoryId));
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect balik
        response.sendRedirect("ManageCategoriesServlet");
    }
}
