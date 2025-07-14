package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Perfume;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CustomerDashboard")
public class CustomerDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Perfume> perfumeList = new ArrayList<>();

        try {
            // Load JDBC Driver (Derby / MySQL ikut setup anda)
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Connect ke database
            Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "SELECT p.perfume_id, p.perfume_name, c.category_name, p.price, p.image_url " +
                         "FROM perfume p JOIN category c ON p.category_id = c.category_id";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Perfume p = new Perfume();
                p.setPerfumeId(rs.getInt("perfume_id"));
                p.setPerfumeName(rs.getString("perfume_name"));
                p.setCategoryName(rs.getString("category_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                perfumeList.add(p);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hantar ke JSP (pastikan file dashboard.jsp ada dalam Web Pages/)
        request.setAttribute("perfumeList", perfumeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
