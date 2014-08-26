package test.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        resp.setContentType("application/json");
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        Gson gson = new Gson();
        JsonObject jObj = new JsonObject();
        Record newRecord = gson.fromJson(json, Record.class);
        if ("".equals(newRecord.getTitle().trim()) || "".equals(newRecord.getContent().trim())) {
            jObj.addProperty("success", false);
            jObj.addProperty("message", "Empty data.");
            resp.getWriter().print(jObj.toString());
            return;
        }
        newRecord.setDate(new Date());
        try {
            HibernateUtil.beginTransaction();
            newRecord.setAuthor(UserDAO.getUserByLogin(req.getSession().getAttribute("login").toString()));
            RecordDAO.addRecord(newRecord);
            HibernateUtil.commitTransaction();
            jObj.addProperty("success", true);
            jObj.addProperty("id", newRecord.getId());
            jObj.addProperty("title", newRecord.getTitle());
            jObj.addProperty("description", newRecord.getDescription());
            jObj.add("date", gson.toJsonTree(newRecord.getDate()));
            //JsonElement record = gson.toJsonTree(newRecord);
            //jObj.add("record", record);
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
            jObj.addProperty("success", false);
            jObj.addProperty("message", "Server error.");
        }
        resp.getWriter().print(jObj.toString());
    }

    @Override
    public void destroy() {
        super.destroy();
        HibernateUtil.closeCurrentSession();
    }
}
