package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class EditPerfumeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        List<Map<String, String>> perfumes = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Perfume");

            while (rs.next()) {
                Map<String, String> perfume = new HashMap<>();
                perfume.put("id", rs.getString("perfume_id"));
                perfume.put("name", rs.getString("perfume_name"));
                perfume.put("category", rs.getString("category_id"));
                perfume.put("price", rs.getString("price"));
                perfume.put("stock", rs.getString("stock"));
                perfumes.add(perfume);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("perfumeList", perfumes);
        RequestDispatcher rd = request.getRequestDispatcher("edit_perfume.jsp");
        rd.forward(request, response);
    }
}
