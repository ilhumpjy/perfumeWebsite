package controller;

import model.Perfume;
import model.Category;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ShopServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Perfume> perfumeList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();

        String selectedCategory = request.getParameter("category");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            // Fetch categories for dropdown
            String catSql = "SELECT * FROM Category";
            Statement catStmt = con.createStatement();
            ResultSet catRs = catStmt.executeQuery(catSql);

            while (catRs.next()) {
                Category c = new Category();
                c.setCategoryId(catRs.getInt("category_id"));
                c.setCategoryName(catRs.getString("category_name"));
                categoryList.add(c);
            }
            catRs.close();
            catStmt.close();

            // Fetch perfumes (filter if selectedCategory exists)
            String sql = "SELECT p.*, c.category_name FROM perfume p JOIN category c ON p.category_id = c.category_id";
            if (selectedCategory != null && !selectedCategory.isEmpty()) {
                sql += " WHERE LOWER(c.category_name) = ?";
            }

            PreparedStatement ps = con.prepareStatement(sql);

            if (selectedCategory != null && !selectedCategory.isEmpty()) {
                ps.setString(1, selectedCategory.toLowerCase());
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Perfume p = new Perfume();
                p.setPerfumeId(rs.getInt("perfume_id"));
                p.setPerfumeName(rs.getString("perfume_name"));
                p.setDescription(rs.getString("description"));
                p.setCategoryName(rs.getString("category_name"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setImageUrl(rs.getString("image_url"));
                perfumeList.add(p);
            }

            rs.close();
            ps.close();
            con.close();

            // Pass data to JSP
            request.setAttribute("perfumeList", perfumeList);
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("selectedCategory", selectedCategory);

            RequestDispatcher dispatcher = request.getRequestDispatcher("shop.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
