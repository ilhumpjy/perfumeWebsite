package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import model.Coupon;

public class DeleteCouponServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int couponId = Integer.parseInt(request.getParameter("coupon_id"));

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Optional: Fetch the coupon details first if needed (for logging / confirmation)
            Coupon coupon = new Coupon();
            String selectSQL = "SELECT * FROM COUPON WHERE COUPON_ID = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
            selectStmt.setInt(1, couponId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                coupon.setCouponId(rs.getInt("COUPON_ID"));
                coupon.setCouponCode(rs.getString("COUPON_CODE"));
                coupon.setCouponDescription(rs.getString("COUPON_DESCRIPTION"));
                coupon.setDiscountPercentage(rs.getDouble("DISCOUNT_PERCENTAGE"));
                coupon.setStartDate(rs.getString("START_DATE"));
                coupon.setEndDate(rs.getString("END_DATE"));
                coupon.setTotalPrice(rs.getDouble("TOTAL_PRICE"));
            }

            // Delete the coupon
            String deleteSQL = "DELETE FROM COUPON WHERE COUPON_ID = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, couponId);
            deleteStmt.executeUpdate();

            conn.close();

            // Optionally set success message in session (optional UI feedback)
            HttpSession session = request.getSession();
            session.setAttribute("deleteMessage", "Coupon [" + coupon.getCouponCode() + "] deleted successfully.");

            response.sendRedirect("ViewCouponServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
