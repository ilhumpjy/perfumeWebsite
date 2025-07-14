package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");

        if (session == null || userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String[] selectedItems = request.getParameterValues("selectedItems");
        String couponCode = request.getParameter("couponCode");
        double totalAmount = 0.0;
        int totalQuantity = 0;
        int couponId = -1;
        double discount = 0.0;

        Connection con = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Step 1: Calculate total amount and quantity
            for (String perfumeIdStr : selectedItems) {
                int perfumeId = Integer.parseInt(perfumeIdStr);
                int quantity = Integer.parseInt(request.getParameter("quantity-" + perfumeId));
                totalQuantity += quantity;

                PreparedStatement ps = con.prepareStatement("SELECT price FROM Perfume WHERE perfume_id=?");
                ps.setInt(1, perfumeId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    double price = rs.getDouble("price");
                    totalAmount += price * quantity;
                }

                rs.close();
                ps.close();
            }

            // Step 2: Validate coupon if 3+ items
            if (couponCode != null && !couponCode.trim().isEmpty() && totalQuantity >= 3) {
                PreparedStatement cps = con.prepareStatement("SELECT * FROM Coupon WHERE coupon_code=? AND CURRENT_DATE BETWEEN start_date AND end_date");
                cps.setString(1, couponCode.trim());
                ResultSet crs = cps.executeQuery();

                if (crs.next()) {
                    couponId = crs.getInt("coupon_id");
                    discount = crs.getDouble("discount_percentage");
                    totalAmount = totalAmount - (totalAmount * discount / 100.0);
                }

                crs.close();
                cps.close();
            }

            // Step 3: Insert into Orders
            PreparedStatement orderStmt = con.prepareStatement(
                    "INSERT INTO Orders (order_date, order_total_amount, user_id, coupon_id, order_status) VALUES (CURRENT_DATE, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            orderStmt.setDouble(1, totalAmount);
            orderStmt.setInt(2, userId);
            if (couponId > 0) {
                orderStmt.setInt(3, couponId);
            } else {
                orderStmt.setNull(3, java.sql.Types.INTEGER);
            }
            orderStmt.setString(4, "Succesfull");
            orderStmt.executeUpdate();

            ResultSet orderKeys = orderStmt.getGeneratedKeys();
            int orderId = -1;
            if (orderKeys.next()) {
                orderId = orderKeys.getInt(1);
            }

            orderStmt.close();

            // Step 4: Insert into Order_Items
            for (String perfumeIdStr : selectedItems) {
                int perfumeId = Integer.parseInt(perfumeIdStr);
                int quantity = Integer.parseInt(request.getParameter("quantity-" + perfumeId));

                PreparedStatement itemStmt = con.prepareStatement(
                        "INSERT INTO Order_Items (order_id, perfume_id, quantity) VALUES (?, ?, ?)");
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, perfumeId);
                itemStmt.setInt(3, quantity);
                itemStmt.executeUpdate();
                itemStmt.close();
            }

            // Step 5: Clear cart
            PreparedStatement clearCart = con.prepareStatement("DELETE FROM Cart_Items WHERE user_id = ?");
            clearCart.setInt(1, userId);
            clearCart.executeUpdate();
            clearCart.close();

            // Step 6: Forward to success page or order summary
            request.setAttribute("orderId", orderId);
            request.setAttribute("discount", discount);
            request.setAttribute("finalAmount", totalAmount);
            request.getRequestDispatcher("checkout_success.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Checkout failed. Try again.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
