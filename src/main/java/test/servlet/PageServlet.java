package test.servlet;

import test.dao.RecordDAO;
import test.entity.Record;

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
        List<Record> records = RecordDAO.getUserRecords((String) session.getAttribute("login"));
        req.setAttribute("records", records);
        req.getRequestDispatcher("/WEB-INF/jsp/page.jsp").forward(req, resp);
    }
}
