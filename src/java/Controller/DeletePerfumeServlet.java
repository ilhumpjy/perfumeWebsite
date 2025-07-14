package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class DeletePerfumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String perfumeId = request.getParameter("perfume_id");
        String oldImage = request.getParameter("old_image");

        System.out.println("DEBUG: perfume_id = " + perfumeId);
        System.out.println("DEBUG: old_image = " + oldImage);

        if (perfumeId == null || perfumeId.trim().isEmpty()) {
            response.getWriter().println("ERROR: perfume_id is missing from the request!");
            return;
        }

        Connection conn = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            conn.setAutoCommit(false);

            // Delete dari Order_Items
            String deleteOrderItems = "DELETE FROM Order_Items WHERE perfume_id = ?";
            PreparedStatement stmt1 = conn.prepareStatement(deleteOrderItems);
            stmt1.setInt(1, Integer.parseInt(perfumeId));
            int deletedOrderItems = stmt1.executeUpdate();
            System.out.println("DEBUG: Order_Items deleted = " + deletedOrderItems);

            // Delete dari Cart_Items
            String deleteCartItems = "DELETE FROM Cart_Items WHERE perfume_id = ?";
            PreparedStatement stmt2 = conn.prepareStatement(deleteCartItems);
            stmt2.setInt(1, Integer.parseInt(perfumeId));
            int deletedCartItems = stmt2.executeUpdate();
            System.out.println("DEBUG: Cart_Items deleted = " + deletedCartItems);

            // Delete dari Perfume
            String deletePerfume = "DELETE FROM Perfume WHERE perfume_id = ?";
            PreparedStatement stmt3 = conn.prepareStatement(deletePerfume);
            stmt3.setInt(1, Integer.parseInt(perfumeId));
            int rowsDeleted = stmt3.executeUpdate();

            System.out.println("DEBUG: Perfume deleted = " + rowsDeleted);

            if (rowsDeleted == 0) {
                conn.rollback();
                response.getWriter().println("Perfume not found in DB. Operation cancelled.");
                return;
            }

            // Delete gambar
            String imagePath = request.getServletContext().getRealPath("/") + oldImage;
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                boolean deleted = imageFile.delete();
                System.out.println("DEBUG: Image deleted = " + deleted);
            } else {
                System.out.println("DEBUG: Image file not found at path = " + imagePath);
            }

            conn.commit();

            stmt1.close();
            stmt2.close();
            stmt3.close();

            response.sendRedirect("EditPerfumeServlet");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
            response.getWriter().println("Error deleting perfume: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
