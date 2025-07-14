package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Hapus session
        HttpSession session = request.getSession(false); // check kalau session wujud
        if (session != null) {
            session.invalidate(); // buang session
        }

        // Redirect ke login page (atau Home)
        response.sendRedirect("admin_login.html");
    }
}
