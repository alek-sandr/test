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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@WebServlet("/page")
public class PageServlet extends HttpServlet {
    private static Random rnd = new Random();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy");
//        List<Record> records = new LinkedList<Record>();
//        for (int i = 0; i < 10; i++) {
//             records.add(new Record(Integer.toString(rnd.nextInt()), dateFormat.format(new Date())));
//        }
        List<Record> records = RecordDAO.getUserRecords((String) session.getAttribute("login"));
        req.setAttribute("records", records);
        req.getRequestDispatcher("/WEB-INF/jsp/page.jsp").forward(req, resp);
    }


}
