package test.servlet;

import test.dao.UserDAO;
import test.entity.User;
import test.util.HibernateUtil;

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
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("authenticated") != null) {
            resp.sendRedirect(context + "/page");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            goToLoginPageWithMessage("Invalid login or password", req, resp);
            return;
        }
        User user = null;
        try {
            HibernateUtil.beginTransaction();
            user = UserDAO.getUserByLogin(login);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
            goToLoginPageWithMessage("Server Error. Try again later.", req, resp);
            return;
        }
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(1800);
            session.setAttribute("login", login);
            session.setAttribute("authenticated", true);
            session.setAttribute("userId", user.getId());
            resp.sendRedirect(context + "/page");
        } else {
            goToLoginPageWithMessage("Invalid login or password", req, resp);
        }
    }

    private void goToLoginPageWithMessage(String message, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        //resp.sendRedirect(req.getContextPath() + "/WEB-INF/jsp/login.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.closeCurrentSession();
    }
}
