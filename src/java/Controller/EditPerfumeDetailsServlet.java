package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import model.Perfume;

public class EditPerfumeDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int perfumeId = Integer.parseInt(request.getParameter("perfume_id"));
        Perfume perfume = new Perfume();
        List<Perfume> categories = new ArrayList<>(); // Guna Perfume untuk simpan category info (categoryId & categoryName)

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Get perfume by ID
            String perfumeSql = "SELECT * FROM Perfume WHERE perfume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(perfumeSql);
            stmt.setInt(1, perfumeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                perfume.setPerfumeId(rs.getInt("perfume_id"));
                perfume.setPerfumeName(rs.getString("perfume_name"));
                perfume.setCategoryId(rs.getInt("category_id"));
                perfume.setPrice(rs.getDouble("price"));
                perfume.setStock(rs.getInt("stock"));
                perfume.setDescription(rs.getString("description"));
                perfume.setImageUrl(rs.getString("image_url")); // fallback gambar lama
            }

            // Get all categories
            String catSql = "SELECT category_id, category_name FROM Category";
            Statement catStmt = conn.createStatement();
            ResultSet catRs = catStmt.executeQuery(catSql);

            while (catRs.next()) {
                Perfume category = new Perfume();
                category.setCategoryId(catRs.getInt("category_id"));
                category.setCategoryName(catRs.getString("category_name"));
                categories.add(category);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("perfume", perfume);
        request.setAttribute("categories", categories);

        // Forward ke edit form (JSP)
        RequestDispatcher rd = request.getRequestDispatcher("edit_perfume_form.jsp");
        rd.forward(request, response);
    }
}
