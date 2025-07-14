package controller;

import model.Perfume;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class CategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String category = request.getParameter("category");
        List<Perfume> perfumeList = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "SELECT p.*, c.category_name FROM perfume p JOIN category c ON p.category_id = c.category_id WHERE c.category_name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, category);
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

            request.setAttribute("perfumeList", perfumeList);
            request.setAttribute("category", category);
            RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
            dispatcher.forward(request, response);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}
