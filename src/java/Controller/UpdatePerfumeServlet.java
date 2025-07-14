package controller;

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

        // Ambil data dari form
        int perfumeId = Integer.parseInt(request.getParameter("perfume_id"));
        String name = request.getParameter("perfume_name");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");
        String oldImage = request.getParameter("old_image"); // Untuk fallback gambar lama

        // Check sama ada admin upload gambar baru atau tidak
        Part filePart = request.getPart("image_file");
        String imageUrl = oldImage;

        if (filePart != null && filePart.getSize() > 0) {
            // Ada gambar baru diupload
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("") + "images";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String uploadPath = uploadDir + File.separator + fileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
            }

            imageUrl = "images/" + fileName; // Update image path
        }

        // Update ke DB
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/PerfumeDB", "app", "app");

            String sql = "UPDATE Perfume SET perfume_name = ?, category_id = ?, price = ?, stock = ?, description = ?, image_url = ? WHERE perfume_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setInt(2, categoryId);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setString(5, description);
            stmt.setString(6, imageUrl);
            stmt.setInt(7, perfumeId);

            stmt.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect ke EditPerfumeServlet (list view)
        response.sendRedirect("EditPerfumeServlet");
    }
}
