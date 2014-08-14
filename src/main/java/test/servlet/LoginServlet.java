package test.servlet;

import test.dao.UserDAO;
import test.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String context = req.getContextPath();
        HttpSession session = req.getSession();

        if (req.getParameter("logout") != null) {
            session.invalidate();
            resp.sendRedirect(context + "/login");
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            User user = UserDAO.getUserByLogin(login);
            if (user != null && user.getPassword().equals(password)) {
                 session.setAttribute("login", login);
                 session.setAttribute("authenticated", true);
                 resp.sendRedirect(context + "/page");
                 return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        //resp.sendRedirect(req.getContextPath() + "/WEB-INF/jsp/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
