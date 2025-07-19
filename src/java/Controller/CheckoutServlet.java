package controller;

import model.Perfume;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("user_id");
        String[] selectedItems = request.getParameterValues("selectedItems");

        if (selectedItems == null || selectedItems.length == 0) {
            response.sendRedirect("CartServlet");
            return;
        }

        List<Perfume> selectedPerfumes = new ArrayList<>();
        Map<Integer, Integer> quantityMap = new HashMap<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            for (String idStr : selectedItems) {
                int id = Integer.parseInt(idStr);
                int qty = Integer.parseInt(request.getParameter("quantity-" + id));

                PreparedStatement ps = con.prepareStatement(
                        "SELECT p.*, c.category_name FROM Perfume p JOIN Category c ON p.category_id = c.category_id WHERE p.perfume_id=?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Perfume p = new Perfume();
                    p.setPerfumeId(rs.getInt("perfume_id"));
                    p.setPerfumeName(rs.getString("perfume_name"));
                    p.setCategoryName(rs.getString("category_name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setStock(qty); // sementara letak quantity dalam stock

                    selectedPerfumes.add(p);
                    quantityMap.put(id, qty);
                }

                rs.close();
                ps.close();
            }

            con.close();

            // Simpan dalam session untuk PaymentServlet
            session.setAttribute("cartItems", selectedPerfumes);
            session.setAttribute("quantityMap", quantityMap);

            response.sendRedirect("checkout.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("CartServlet");
        }
    }
}
