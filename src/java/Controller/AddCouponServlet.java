package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import model.Coupon;

public class AddCouponServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String code = request.getParameter("coupon_code");
        String desc = request.getParameter("coupon_description");
        double discount = Double.parseDouble(request.getParameter("discount_percentage"));
        String start = request.getParameter("start_date");
        String end = request.getParameter("end_date");
        double totalPrice = Double.parseDouble(request.getParameter("total_price"));

        Coupon coupon = new Coupon();
        coupon.setCouponCode(code);
        coupon.setCouponDescription(desc);
        coupon.setDiscountPercentage(discount);
        coupon.setStartDate(start);
        coupon.setEndDate(end);
        coupon.setTotalPrice(totalPrice);

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO COUPON (COUPON_CODE, COUPON_DESCRIPTION, DISCOUNT_PERCENTAGE, START_DATE, END_DATE, TOTAL_PRICE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, coupon.getCouponCode());
            stmt.setString(2, coupon.getCouponDescription());
            stmt.setDouble(3, coupon.getDiscountPercentage());
            stmt.setString(4, coupon.getStartDate());
            stmt.setString(5, coupon.getEndDate());
            stmt.setDouble(6, coupon.getTotalPrice());

            stmt.executeUpdate();
            conn.close();

            response.sendRedirect("ViewCouponServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setAttribute("coupon", new Coupon());
        RequestDispatcher rd = request.getRequestDispatcher("add_coupon.jsp");
        rd.forward(request, response);
    }
}
