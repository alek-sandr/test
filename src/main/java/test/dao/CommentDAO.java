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

    public static List<Comment> getRecordComments(Record record) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Comment> comments = session.createQuery("FROM Comment WHERE record = :rec ORDER BY date ASC")
                .setEntity("rec", record).list();
        return comments;
    }

    public static List<Comment> getRecordCommentsByID(Long recordId) {
        Record record = RecordDAO.getRecordById(recordId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Comment> comments = session.createQuery("FROM Comment WHERE record = :rec ORDER BY date ASC")
                .setEntity("rec", record).list();
        return comments;
    }
}
