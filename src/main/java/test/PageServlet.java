package test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm | dd.MM.yyyy");
        List<Record> records = new LinkedList<Record>();
        for (int i = 0; i < 10; i++) {
             records.add(new Record(Integer.toString(rnd.nextInt()), dateFormat.format(new Date())));
        }
        req.setAttribute("records", records);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }


}