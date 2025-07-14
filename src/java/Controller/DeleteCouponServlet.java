package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteCouponServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int couponId = Integer.parseInt(request.getParameter("coupon_id"));

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM COUPON WHERE COUPON_ID = ?");
            stmt.setInt(1, couponId);
            stmt.executeUpdate();

            conn.close();
            response.sendRedirect("ViewCouponServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
