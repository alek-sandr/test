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
    String context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = getServletContext().getContextPath();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login != null && password != null) {
            User user = UserDAO.getUserByLogin(login);
            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute("login", login);
                session.setAttribute("authenticated", true);
                resp.sendRedirect(context + "/page");
                return;
            }
        }
        req.setAttribute("message", "Invalid login or password. Try again.");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        //resp.sendRedirect(req.getContextPath() + "/WEB-INF/jsp/login.jsp");
    }
}
