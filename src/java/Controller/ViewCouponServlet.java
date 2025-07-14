package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ViewCouponServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Map<String, Object>> coupons = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COUPON");

            while (rs.next()) {
                Map<String, Object> coupon = new HashMap<>();
                coupon.put("id", rs.getInt("COUPON_ID"));
                coupon.put("code", rs.getString("COUPON_CODE"));
                coupon.put("description", rs.getString("COUPON_DESCRIPTION"));
                coupon.put("discount", rs.getDouble("DISCOUNT_PERCENTAGE"));
                coupon.put("start", rs.getDate("START_DATE"));
                coupon.put("end", rs.getDate("END_DATE"));
                coupons.add(coupon);
            }

            conn.close();
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("coupons", coupons);
        RequestDispatcher rd = request.getRequestDispatcher("view_coupon.jsp");
        rd.forward(request, response);
    }
}