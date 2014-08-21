package test.dao;

import org.hibernate.Session;
import test.entity.User;
import test.util.HibernateUtil;

import java.util.List;

public class UserDAO {

    public static Long getUserId(String login) {
        return getUserByLogin(login).getId();
    }

    public static User getUserByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User U WHERE U.login = :login").setString("login", login).list();
        session.getTransaction().commit();
        session.close();
        if (users.size() == 1) return users.get(0);
        else if (users.size() == 0) return null;
        else throw new IllegalStateException("Users with duplicate logins in DB");
    }

    public static boolean addUser(User user) {
        User temp = getUserByLogin(user.getLogin());
        if (temp != null) return false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
