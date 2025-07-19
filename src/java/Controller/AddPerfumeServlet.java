package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import model.Perfume;

@MultipartConfig
public class AddPerfumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dapatkan data dari form
        String perfumeName = request.getParameter("perfume_name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");

        // Handle image upload
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

        // Set dalam Perfume model
        Perfume perfume = new Perfume();
        perfume.setPerfumeName(perfumeName);
        perfume.setCategoryId(categoryId);  // Pastikan ada setter categoryId dalam Perfume.java
        perfume.setPrice(price);
        perfume.setStock(stock);
        perfume.setDescription(description);
        perfume.setImageUrl(imageUrl);

        // DB Operation (terus dalam servlet - MVC basic tanpa DAO)
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "INSERT INTO Perfume (perfume_name, category_id, price, stock, description, image_url) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, perfume.getPerfumeName());
            ps.setInt(2, perfume.getCategoryId());   // ✅ guna getter dari Perfume
            ps.setDouble(3, perfume.getPrice());
            ps.setInt(4, perfume.getStock());
            ps.setString(5, perfume.getDescription());
            ps.setString(6, perfume.getImageUrl());

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
