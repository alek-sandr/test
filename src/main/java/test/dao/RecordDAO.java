package test.dao;

import org.hibernate.Session;
import test.entity.Record;
import test.util.HibernateUtil;

import java.util.List;

public class RecordDAO {

    public static void addRecord(Record record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close();
    }

    public static void addRecords(List<Record> recordList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (Record r : recordList) {
            session.save(r);
        }
        session.getTransaction().commit();
        session.close();
    }

    public static List<Record> getUserRecords(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long id = UserDAO.getUserId(login);
        List<Record> records = session.createQuery("from Record R where R.ownerId = :userId").setLong("userId", id).list();
        session.getTransaction().commit();
        session.close();
        return records;
    }
}
