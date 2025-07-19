package controller;

import model.Coupon;
import model.Perfume;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        List<Perfume> cartItems = (List<Perfume>) session.getAttribute("cartItems");
        Map<Integer, Integer> quantityMap = (Map<Integer, Integer>) session.getAttribute("quantityMap");
        Integer userId = (Integer) session.getAttribute("user_id");

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("CartServlet");
            return;
        }

        double grandTotal = 0;
        for (Perfume p : cartItems) {
            int quantity = quantityMap.get(p.getPerfumeId());
            grandTotal += quantity * p.getPrice();
        }

        String couponCode = request.getParameter("couponCode");
        double discount = 0;
        int couponId = 0;

        Connection conn = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");
            conn.setAutoCommit(false); // start transaction

            // Handle coupon if provided
            if (couponCode != null && !couponCode.trim().isEmpty()) {
                String sql = "SELECT * FROM coupon WHERE coupon_code = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, couponCode);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Coupon coupon = new Coupon();
                    coupon.setCouponId(rs.getInt("coupon_id"));
                    coupon.setCouponCode(rs.getString("coupon_code"));
                    coupon.setDiscountPercentage(rs.getDouble("discount_percentage"));
                    coupon.setStartDate(rs.getString("start_date"));
                    coupon.setEndDate(rs.getString("end_date"));
                    coupon.setTotalPrice(rs.getDouble("total_price"));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate today = LocalDate.now();
                    LocalDate start = LocalDate.parse(coupon.getStartDate(), formatter);
                    LocalDate end = LocalDate.parse(coupon.getEndDate(), formatter);

                    if (grandTotal >= coupon.getTotalPrice() &&
                            !today.isBefore(start) &&
                            !today.isAfter(end)) {
                        discount = grandTotal * (coupon.getDiscountPercentage() / 100.0);
                        couponId = coupon.getCouponId();
                    } else {
                        session.setAttribute("error", "Coupon invalid: expired or minimum price not met.");
                        response.sendRedirect("checkout.jsp");
                        return;
                    }

                } else {
                    session.setAttribute("error", "Coupon code not found.");
                    response.sendRedirect("checkout.jsp");
                    return;
                }
            }

            double finalTotal = grandTotal - discount;

            // Insert into Orders table
            String insertOrder = "INSERT INTO Orders (order_date, order_total_amount, user_id, coupon_id, order_status) VALUES (CURRENT_DATE, ?, ?, ?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setDouble(1, finalTotal);
            psOrder.setInt(2, userId);
            if (couponId == 0) {
                psOrder.setNull(3, Types.INTEGER);
            } else {
                psOrder.setInt(3, couponId);
            }
            psOrder.setString(4, "Pending"); // initially pending
            psOrder.executeUpdate();

            ResultSet orderKeys = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (orderKeys.next()) {
                orderId = orderKeys.getInt(1);
            }

            // Insert items into Order_Items table
            for (Perfume p : cartItems) {
                int qty = quantityMap.get(p.getPerfumeId());
                String insertItem = "INSERT INTO Order_Items (order_id, perfume_id, quantity) VALUES (?, ?, ?)";
                PreparedStatement psItem = conn.prepareStatement(insertItem);
                psItem.setInt(1, orderId);
                psItem.setInt(2, p.getPerfumeId());
                psItem.setInt(3, qty);
                psItem.executeUpdate();
                psItem.close();
            }

            // Insert into Payment table
            String method = request.getParameter("method");
            String insertPayment = "INSERT INTO Payment (order_id, payment_date, amount, payment_method, status) VALUES (?, CURRENT_DATE, ?, ?, ?)";
            PreparedStatement psPayment = conn.prepareStatement(insertPayment);
            psPayment.setInt(1, orderId);
            psPayment.setDouble(2, finalTotal);
            psPayment.setString(3, method);
            psPayment.setString(4, "Completed");
            psPayment.executeUpdate();
            psPayment.close();

            // ✅ Update order status to 'Completed' after payment success
            String updateOrderStatus = "UPDATE Orders SET order_status = 'Completed' WHERE order_id = ?";
            PreparedStatement psUpdate = conn.prepareStatement(updateOrderStatus);
            psUpdate.setInt(1, orderId);
            psUpdate.executeUpdate();
            psUpdate.close();

            // Delete cart items in DB (if applicable)
            String deleteCartSql = "DELETE FROM Cart_Items WHERE user_id = ?";
            PreparedStatement psDeleteCart = conn.prepareStatement(deleteCartSql);
            psDeleteCart.setInt(1, userId);
            psDeleteCart.executeUpdate();
            psDeleteCart.close();

            // ✅ Commit the transaction
            conn.commit();

            // ✅ Set orderId in session so it can be shown on success page
            session.setAttribute("orderId", orderId);

            // Clear cart session data
            session.removeAttribute("cartItems");
            session.removeAttribute("quantityMap");

            // Set session data for success page
            session.setAttribute("finalTotal", String.format("%.2f", finalTotal));
            session.setAttribute("discount", String.format("%.2f", discount));
            session.setAttribute("paymentMethod", method);

            // Redirect to success page
            response.sendRedirect("checkout_success.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // roll back on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            session.setAttribute("error", "Error processing payment.");
            response.sendRedirect("checkout.jsp");
        } finally {
            try {
                if (conn != null) conn.close(); // always close connection
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
