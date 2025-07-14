package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import controller.UserBean;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("user_username");
        String password = request.getParameter("user_password");

        UserBean bean = new UserBean();
        bean.setUsername(username);
        bean.setPassword(password);

        if (bean.validate()) {
            HttpSession session = request.getSession();
            session.setAttribute("user_username", username);
            session.setAttribute("user_id", bean.getUserId()); // ✅ Fix: store user_id

            response.sendRedirect("CustomerDashboardServlet");
        } else {
            response.sendRedirect("UserLogin.jsp?error=1");
        }
    }
}
