package controller;

import model.Perfume;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("user_id");

        // 🟡 Redirect if not logged in
        if (userId == null) {
            response.sendRedirect("UserLogin.jsp");
            return;
        }

        List<Perfume> cartItems = new ArrayList<>();

        try {
            // 🟡 Connect to Derby DB
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // 🟡 Query with join: Cart_Items + Perfume + Category
            String sql = "SELECT p.*, ci.quantity, c.category_name " +
                         "FROM Cart_Items ci " +
                         "JOIN Perfume p ON ci.perfume_id = p.perfume_id " +
                         "JOIN Category c ON p.category_id = c.category_id " +
                         "WHERE ci.user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            // 🟡 Collect each cart item
            while (rs.next()) {
                Perfume p = new Perfume();
                p.setPerfumeId(rs.getInt("perfume_id"));
                p.setPerfumeName(rs.getString("perfume_name"));
                p.setCategoryName(rs.getString("category_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setStock(rs.getInt("quantity")); // store cart quantity in stock field
                cartItems.add(p);
            }

            // 🟡 Clean up
            rs.close();
            ps.close();
            con.close();

            // 🟢 Pass list to JSP
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("cart.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // ✅ Also check your NetBeans Output/GlassFish Console
            request.setAttribute("error", "Something went wrong while loading the cart.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
