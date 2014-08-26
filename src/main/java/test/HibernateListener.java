package test;

import test.dao.RecordDAO;
import test.dao.UserDAO;
import test.entity.Record;
import test.entity.User;
import test.util.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@WebListener
public class HibernateListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory();
        Random rnd = new Random();
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        List<Record> records = new LinkedList<Record>();
        for (int i = 0; i < 10; i++) {
            Record record = new Record("Record", "Description", Integer.toString(rnd.nextInt()), new Date());
            record.setAuthor(admin);
            records.add(record);
        }
        try {
            HibernateUtil.beginTransaction();
            UserDAO.addUser(admin);
            RecordDAO.addRecords(records);
            HibernateUtil.commitTransaction();
        } catch (Exception e) {
            HibernateUtil.rollbackTransaction();
            //TODO: log exception
        } finally {
            HibernateUtil.closeCurrentSession();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory().close();
    }
}
