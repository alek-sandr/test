package test.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import test.entity.Record;
import test.util.HibernateUtil;

import java.util.List;

public class RecordDAO {

    public static boolean addRecord(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close();
        return t.wasCommitted();
    }

    public static boolean addRecords(List<Record> recordList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        for (Record r : recordList) {
            session.save(r);
        }
        session.getTransaction().commit();
        session.close();
        return t.wasCommitted();
    }

    public static List<Record> getUserRecords(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long id = UserDAO.getUserId(login);
        List<Record> records = session.createQuery("FROM Record R WHERE R.ownerId = :userId ORDER BY R.date DESC").setLong("userId", id).list();
        session.getTransaction().commit();
        session.close();
        return records;
    }
}
