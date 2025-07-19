package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import model.Coupon;

public class ViewCouponServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Coupon> couponList = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "SELECT * FROM COUPON";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Coupon coupon = new Coupon();
                coupon.setCouponId(rs.getInt("COUPON_ID"));
                coupon.setCouponCode(rs.getString("COUPON_CODE"));
                coupon.setCouponDescription(rs.getString("COUPON_DESCRIPTION"));
                coupon.setDiscountPercentage(rs.getDouble("DISCOUNT_PERCENTAGE"));
                coupon.setStartDate(rs.getString("START_DATE"));
                coupon.setEndDate(rs.getString("END_DATE"));
                coupon.setTotalPrice(rs.getDouble("TOTAL_PRICE"));

                couponList.add(coupon);
            }

            conn.close();
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("coupons", couponList);
        RequestDispatcher rd = request.getRequestDispatcher("view_coupon.jsp");
        rd.forward(request, response);
    }
}
