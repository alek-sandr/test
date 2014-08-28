package test.dao;

import org.hibernate.Session;
import test.entity.Record;
import test.entity.User;
import test.util.HibernateUtil;

import java.util.List;

public class RecordDAO {

    public static void addRecord(Record record) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.save(record);
    }

    public static List<Record> getUserRecords(String login) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        User author = UserDAO.getUserByLogin(login);
        List<Record> records = session.createQuery("FROM Record WHERE author = :author ORDER BY date DESC").setEntity("author", author).list();
        return records;
    }

    public static Record getRecordById(long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Record> records = session.createQuery("FROM Record WHERE id = :Id").setLong("Id", id).list();
        return records.size() > 0 ? records.get(0) : null;
    }
}
