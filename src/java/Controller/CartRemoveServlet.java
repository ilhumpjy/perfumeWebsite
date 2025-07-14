package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CartRemoveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        String perfumeId = request.getParameter("perfumeId");

        if (perfumeId == null || perfumeId.trim().isEmpty()) {
            response.getWriter().write("FAIL: Missing perfumeId");
            return;
        }

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "DELETE FROM Cart_Items WHERE perfume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(perfumeId));

            int rows = stmt.executeUpdate();
            conn.close();

            if (rows > 0) {
                response.getWriter().write("SUCCESS");
            } else {
                response.getWriter().write("FAIL: Not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("FAIL: Exception");
        }
    }
}
