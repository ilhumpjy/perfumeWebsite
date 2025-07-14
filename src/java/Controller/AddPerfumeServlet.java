package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

@MultipartConfig
public class AddPerfumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String perfumeName = request.getParameter("perfume_name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");

        Part filePart = request.getPart("image_file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String imagesPath = getServletContext().getRealPath("/images");
        File uploadDir = new File(imagesPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String uploadPath = imagesPath + File.separator + fileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
        }

        String imageUrl = "images/" + fileName;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO Perfume (perfume_name, category_id, price, stock, description, image_url) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, perfumeName);
            ps.setInt(2, categoryId);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            ps.setString(5, description);
            ps.setString(6, imageUrl);

            ps.executeUpdate();
            conn.close();

            response.sendRedirect("ListPerfumeServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("GetCategoriesServlet");
    }
}
