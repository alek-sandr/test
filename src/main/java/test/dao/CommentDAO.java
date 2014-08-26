package test.dao;

import org.hibernate.Session;
import test.entity.Comment;
import test.entity.Record;
import test.util.HibernateUtil;

import java.util.List;

public class CommentDAO {

    public static void addComment(Comment comment) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.save(comment);
    }

    public static List<Comment> getCommentsByRecordId(Long recordId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Comment> comments = session.createQuery("FROM Comment WHERE recordId = :recId ORDER BY date ASC")
                .setLong("recId", recordId).list();
        return comments;
    }
}
