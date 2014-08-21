package test.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import test.dao.RecordDAO;
import test.dao.UserDAO;
import test.entity.Record;

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
        newRecord.setOwnerId(UserDAO.getUserId(req.getSession().getAttribute("login").toString()));
        boolean isSuccess = RecordDAO.addRecord(newRecord);
        if (isSuccess) {
            jObj.addProperty("success", true);
            JsonElement record = gson.toJsonTree(newRecord);
            jObj.add("record", record);
        } else {
            jObj.addProperty("success", false);
            jObj.addProperty("message", "Server error.");
        }
        resp.getWriter().print(jObj.toString());
    }
}
