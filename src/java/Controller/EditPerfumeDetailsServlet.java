package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class EditPerfumeDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int perfumeId = Integer.parseInt(request.getParameter("perfume_id"));
        Map<String, String> perfume = new HashMap<>();
        List<Map<String, String>> categories = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Get perfume by ID
            String perfumeSql = "SELECT * FROM Perfume WHERE perfume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(perfumeSql);
            stmt.setInt(1, perfumeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                perfume.put("id", rs.getString("perfume_id"));
                perfume.put("name", rs.getString("perfume_name"));
                perfume.put("category", rs.getString("category_id"));
                perfume.put("price", rs.getString("price"));
                perfume.put("stock", rs.getString("stock"));
                perfume.put("description", rs.getString("description"));
                perfume.put("image", rs.getString("image_url")); // <-- untuk fallback gambar lama
            }

            // Get all categories
            String catSql = "SELECT category_id, category_name FROM Category";
            Statement catStmt = conn.createStatement();
            ResultSet catRs = catStmt.executeQuery(catSql);

            while (catRs.next()) {
                Map<String, String> category = new HashMap<>();
                category.put("id", catRs.getString("category_id"));
                category.put("name", catRs.getString("category_name"));
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
