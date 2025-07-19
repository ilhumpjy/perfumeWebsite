package controller;

import model.Perfume;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import java.nio.file.*;
import java.sql.*;

@MultipartConfig
public class UpdatePerfumeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Create perfume object & isi data dari form
        Perfume perfume = new Perfume();

        perfume.setPerfumeId(Integer.parseInt(request.getParameter("perfume_id")));
        perfume.setPerfumeName(request.getParameter("perfume_name"));
        perfume.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
        perfume.setPrice(Double.parseDouble(request.getParameter("price")));
        perfume.setStock(Integer.parseInt(request.getParameter("stock")));
        perfume.setDescription(request.getParameter("description"));

        String oldImage = request.getParameter("old_image");
        perfume.setImageUrl(oldImage); // Set dulu gambar lama

        // Handle gambar baru kalau ada
        Part filePart = request.getPart("image_file");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String imagesDir = getServletContext().getRealPath("/images");
            File dir = new File(imagesDir);
            if (!dir.exists()) dir.mkdirs();

            String uploadPath = imagesDir + File.separator + fileName;

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
            }

            perfume.setImageUrl("images/" + fileName); // Overwrite dengan gambar baru
        }

        // Update ke DB guna data dari perfume object
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "UPDATE Perfume SET perfume_name = ?, category_id = ?, price = ?, stock = ?, description = ?, image_url = ? WHERE perfume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, perfume.getPerfumeName());
            stmt.setInt(2, perfume.getCategoryId());
            stmt.setDouble(3, perfume.getPrice());
            stmt.setInt(4, perfume.getStock());
            stmt.setString(5, perfume.getDescription());
            stmt.setString(6, perfume.getImageUrl());
            stmt.setInt(7, perfume.getPerfumeId());

            stmt.executeUpdate();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error updating perfume: " + e.getMessage());
            return;
        }

        // Redirect ke list view
        response.sendRedirect("EditPerfumeServlet");
    }
}
