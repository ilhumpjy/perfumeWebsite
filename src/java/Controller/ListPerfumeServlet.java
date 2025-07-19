package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import model.Perfume;

public class ListPerfumeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Perfume> perfumeList = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Perfume");

            while (rs.next()) {
                Perfume perfume = new Perfume();
                perfume.setPerfumeId(rs.getInt("perfume_id"));
                perfume.setPerfumeName(rs.getString("perfume_name"));
                perfume.setCategoryId(rs.getInt("category_id"));
                perfume.setPrice(rs.getDouble("price"));
                perfume.setStock(rs.getInt("stock"));
                perfume.setDescription(rs.getString("description"));
                perfume.setImageUrl(rs.getString("image_url"));

                perfumeList.add(perfume);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("perfumes", perfumeList);
        RequestDispatcher rd = request.getRequestDispatcher("list_perfume.jsp");
        rd.forward(request, response);
    }
}