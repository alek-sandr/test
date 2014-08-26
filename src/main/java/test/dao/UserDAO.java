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
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<User> users = session.createQuery("FROM User WHERE login = :login").setString("login", login).list();
        if (users.size() == 1) return users.get(0);
        else if (users.size() == 0) return null;
        else throw new IllegalStateException("Users with duplicate logins in DB");
    }

    public static void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.save(user);
    }
}
