package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String context = req.getContextPath();
        HttpSession session = req.getSession(true);
        if (isAuthorised(session)) {
            resp.sendRedirect(context + "/index.jsp");
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {
            if (login.equals("admin") && password.equals("admin")) {
                session.setAttribute("login", login);
                session.setAttribute("auth", true);
                resp.sendRedirect(context + "/index.jsp");
                return;
            }
        }
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static boolean isAuthorised(HttpSession session) {
        Object authorised = session.getAttribute("auth");
        if (authorised != null) {
            return (Boolean) authorised;
        }
        return false;
    }
}
