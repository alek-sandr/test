package test.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import test.dao.CommentDAO;
import test.dao.RecordDAO;
import test.dao.UserDAO;
import test.entity.Comment;
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

    /**
     * accepts JSON string in format
     * {
     *     "content":string,
     *     "recordId":integer value
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
        Long recordId = null;
        String content = null;
        try {
            JsonObject data = gson.fromJson(json, JsonObject.class);
            recordId = data.get("recordId").getAsLong();
            content = data.get("content").getAsString();
        } catch (Exception e) {
            answerData.addProperty("success", false);
            answerData.addProperty("message", "Wrong data.");
            resp.getWriter().print(answerData.toString());
            return;
        }
        Comment newComment = new Comment();
        if ("".equals(content.trim())) {// empty comment
            answerData.addProperty("success", false);
            answerData.addProperty("message", "Empty comment.");
            resp.getWriter().print(answerData.toString());
            return;
        }
        try {
            HibernateUtil.beginTransaction();
            newComment.setContent(content);
            newComment.setDate(new Date());
            newComment.setAuthor(UserDAO.getUserByLogin(req.getSession().getAttribute("login").toString()));
            newComment.setRecord(RecordDAO.getRecordById(recordId));
            CommentDAO.addComment(newComment);
            HibernateUtil.commitTransaction();
            answerData.addProperty("success", true);
            answerData.addProperty("username", req.getSession().getAttribute("login").toString());
            answerData.addProperty("content", newComment.getContent());
            answerData.add("date", gson.toJsonTree(newComment.getDate()));
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
