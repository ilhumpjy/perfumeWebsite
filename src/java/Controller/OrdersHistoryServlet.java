package controller;

import model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import model.OrderBean;
import model.OrderItem;

public class OrdersHistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<OrderBean> orderList = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Query all orders
            String sql = "SELECT o.order_id, o.order_date, o.order_total_amount, o.order_status, c.coupon_code, oi.quantity, p.perfume_name, p.price " +
                         "FROM Orders o " +
                         "LEFT JOIN Coupon c ON o.coupon_id = c.coupon_id " +
                         "JOIN Order_Items oi ON o.order_id = oi.order_id " +
                         "JOIN Perfume p ON oi.perfume_id = p.perfume_id " +
                         "WHERE o.user_id = ? ORDER BY o.order_date DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            Map<Integer, OrderBean> orderMap = new LinkedHashMap<>();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                OrderBean order = orderMap.get(orderId);
                if (order == null) {
                    order = new OrderBean();
                    order.setId(orderId);
                    order.setDate(rs.getDate("order_date"));
                    order.setAmount(rs.getDouble("order_total_amount"));
                    order.setStatus(rs.getString("order_status"));
                    order.setCouponCode(rs.getString("coupon_code"));
                    order.setItems(new ArrayList<>());
                    orderMap.put(orderId, order);
                }

                OrderItem item = new OrderItem();
                item.setPerfumeName(rs.getString("perfume_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));

                order.getItems().add(item);
            }

            rs.close();
            ps.close();
            con.close();

            orderList.addAll(orderMap.values());
            request.setAttribute("orders", orderList);
            request.getRequestDispatcher("orderHistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load your order history.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
