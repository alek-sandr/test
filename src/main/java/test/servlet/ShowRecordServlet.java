package test.servlet;

import test.dao.CommentDAO;
import test.dao.RecordDAO;
import test.entity.Comment;
import test.entity.Record;
import test.util.HibernateUtil;
import test.util.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/record")
public class ShowRecordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = req.getParameter("id");
        if (id == null) {
            ServletHelper.goToPageNotFound(req, resp);
            return;
        }
        Long recId = null;
        try {
            recId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            ServletHelper.goToPageNotFound(req, resp);
            return;
        }
        Record record = null;
        List<Comment> comments = null;
        try {
            HibernateUtil.beginTransaction();
            record = RecordDAO.getRecordById(recId);
            if (record != null) {
                comments = CommentDAO.getCommentsByRecordId(recId);
            }
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
            throw new ServletException(e);
        }
        if (record == null) {
            ServletHelper.goToPageNotFound(req, resp);
            return;
        }
        if (record.getAuthor().getId().equals((Long) session.getAttribute("userId"))) {
            req.setAttribute("record", record);
            req.setAttribute("comments", comments);
            req.getRequestDispatcher("/WEB-INF/jsp/record.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/jsp/forbidden.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.closeCurrentSession();
    }
}
