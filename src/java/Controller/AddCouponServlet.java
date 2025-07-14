package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddCouponServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String code = request.getParameter("coupon_code");
        String desc = request.getParameter("coupon_description");
        double discount = Double.parseDouble(request.getParameter("discount_percentage"));
        String start = request.getParameter("start_date");
        String end = request.getParameter("end_date");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO COUPON (COUPON_CODE, COUPON_DESCRIPTION, DISCOUNT_PERCENTAGE, START_DATE, END_DATE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.setString(2, desc);
            stmt.setDouble(3, discount);
            stmt.setString(4, start);
            stmt.setString(5, end);
            stmt.executeUpdate();

            conn.close();
            response.sendRedirect("ViewCouponServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    // Optional: support GET to access form directly
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("add_coupon.jsp");
        rd.forward(request, response);
    }
}
