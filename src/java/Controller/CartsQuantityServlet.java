package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CartsQuantityServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String perfumeIdStr = request.getParameter("perfumeId");
        String quantityStr = request.getParameter("quantity");

        if (perfumeIdStr == null || quantityStr == null || perfumeIdStr.isEmpty() || quantityStr.isEmpty()) {
            out.print("FAIL: Missing parameters");
            return;
        }

        try {
            int perfumeId = Integer.parseInt(perfumeIdStr);
            int quantity = Integer.parseInt(quantityStr);

            // Connect to Derby DB
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Update quantity for that perfumeId
            String sql = "UPDATE Cart_Items SET quantity = ? WHERE perfume_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, perfumeId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.print("SUCCESS");
            } else {
                out.print("FAIL: No matching record found");
            }

            ps.close();
            conn.close();

        } catch (NumberFormatException e) {
            out.print("FAIL: Invalid number format");
        } catch (Exception e) {
            out.print("FAIL: " + e.getMessage());
        }
    }
}
