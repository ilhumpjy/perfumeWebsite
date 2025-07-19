package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.UserBean;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("user_username");
        String password = request.getParameter("user_password");

        UserBean bean = new UserBean();
        bean.setUser_username(username);
        bean.setUser_password(password); // ✅ Betulkan case

        if (bean.validate()) {
            HttpSession session = request.getSession();
            session.setAttribute("user_username", bean.getUser_username()); // Simpan dari bean
            session.setAttribute("user_id", bean.getUser_id()); // ✅ Betulkan method

            // Optional: simpan nama/email untuk guna dalam dashboard
            session.setAttribute("user_name", bean.getUser_name());
            session.setAttribute("user_email", bean.getUser_email());

            response.sendRedirect("CustomerDashboardServlet");
        } else {
            response.sendRedirect("UserLogin.jsp?error=1");
        }
    }
}
