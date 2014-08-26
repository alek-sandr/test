package test.servlet;

import test.dao.RecordDAO;
import test.entity.Record;
import test.util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/page")
public class PageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Record> records = null;
        try {
            HibernateUtil.beginTransaction();
            records = RecordDAO.getUserRecords((String) session.getAttribute("login"));
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
            throw new ServletException(e);
        }
        req.setAttribute("records", records);
        req.getRequestDispatcher("/WEB-INF/jsp/page.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.closeCurrentSession();
    }
}
