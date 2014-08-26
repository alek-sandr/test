package test.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import test.dao.CommentDAO;
import test.dao.RecordDAO;
import test.dao.UserDAO;
import test.entity.Comment;
import test.entity.User;
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

@WebServlet("/addcomment")
public class AddCommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        resp.setContentType("application/json");
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        Gson gson = new Gson();
        //Long recordId = gson.fromJson(json, JsonObject.class).get("recordId").getAsLong();
        JsonObject jObj = new JsonObject();
        Comment newComment = gson.fromJson(json, Comment.class);
        if ("".equals(newComment.getContent().trim())) {// empty comment
            jObj.addProperty("success", false);
            jObj.addProperty("message", "Empty data.");
            resp.getWriter().print(jObj.toString());
            return;
        }
        newComment.setDate(new Date());
        try {
            HibernateUtil.beginTransaction();
            newComment.setAuthor(UserDAO.getUserByLogin(req.getSession().getAttribute("login").toString()));
            //newComment.setRecordId(recordId);
            CommentDAO.addComment(newComment);
            HibernateUtil.commitTransaction();
            jObj.addProperty("success", true);
            jObj.addProperty("username", req.getSession().getAttribute("login").toString());
            jObj.addProperty("content", newComment.getContent());
            jObj.add("date", gson.toJsonTree(newComment.getDate()));

            //JsonElement comment = gson.toJsonTree(newComment);
            //jObj.add("comment", comment);
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
