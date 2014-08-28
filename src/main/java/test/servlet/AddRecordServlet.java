package test.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import test.dao.RecordDAO;
import test.dao.UserDAO;
import test.entity.Record;
import test.util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@WebServlet("/addrecord")
public class AddRecordServlet extends HttpServlet {

    /**
     * accepts JSON string in format
     * {
     *     "title":string,
     *     "description":string
     *     "content":string,
     * }
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        resp.setContentType("application/json");
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        Gson gson = new Gson();
        JsonObject answerData = new JsonObject();
        String title = null;
        String description = null;
        String content = null;
        try {
            JsonObject data = gson.fromJson(json, JsonObject.class);
            title = data.get("title").getAsString();
            description = data.get("description").getAsString();
            content = data.get("content").getAsString();
        } catch (Exception e) {
            answerData.addProperty("success", false);
            answerData.addProperty("message", "Wrong data.");
            resp.getWriter().print(answerData.toString());
            return;
        }
        Record newRecord = new Record();//gson.fromJson(json, Record.class);
        if ("".equals(title.trim()) || "".equals(content.trim())) {
            answerData.addProperty("success", false);
            answerData.addProperty("message", "Empty data.");
            resp.getWriter().print(answerData.toString());
            return;
        }
        try {
            HibernateUtil.beginTransaction();
            newRecord.setTitle(title);
            newRecord.setDescription(description);
            newRecord.setContent(content);
            newRecord.setDate(new Date());
            newRecord.setAuthor(UserDAO.getUserByLogin(req.getSession().getAttribute("login").toString()));
            RecordDAO.addRecord(newRecord);
            HibernateUtil.commitTransaction();
            answerData.addProperty("success", true);
            answerData.addProperty("id", newRecord.getId());
            answerData.addProperty("title", newRecord.getTitle());
            answerData.addProperty("description", newRecord.getDescription());
            answerData.add("date", gson.toJsonTree(newRecord.getDate()));
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
            answerData.addProperty("success", false);
            answerData.addProperty("message", "Server error.");
        }
        resp.getWriter().print(answerData.toString());
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.closeCurrentSession();
    }
}
