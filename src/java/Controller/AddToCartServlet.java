package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int perfumeId = Integer.parseInt(request.getParameter("perfume_id"));
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Check if item already exists in cart
            String checkSql = "SELECT quantity FROM Cart_Items WHERE user_id = ? AND perfume_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, perfumeId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                String updateSql = "UPDATE Cart_Items SET quantity = ? WHERE user_id = ? AND perfume_id = ?";
                PreparedStatement updateStmt = con.prepareStatement(updateSql);
                updateStmt.setInt(1, currentQty + 1);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, perfumeId);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                String insertSql = "INSERT INTO Cart_Items (user_id, perfume_id, quantity) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, perfumeId);
                insertStmt.setInt(3, 1);
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            rs.close();
            checkStmt.close();
            con.close();

            // Set success message for SweetAlert2 in session
            session.setAttribute("message", "Item successfully added to cart!");
            response.sendRedirect("ShopServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
