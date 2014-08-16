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
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        UserDAO.addUser(admin);
        Random rnd = new Random();
        List<Record> records = new LinkedList<Record>();
        for (int i = 0; i < 10; i++) {
            Record record = new Record(Integer.toString(rnd.nextInt()), new Date());
            record.setOwnerId(UserDAO.getUserId("admin"));
            records.add(record);
        }
        RecordDAO.addRecords(records);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HibernateUtil.getSessionFactory().close();
    }
}
