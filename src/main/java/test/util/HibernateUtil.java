package test.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sf;

    static {
        try {
            Configuration cfg = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            sf = cfg.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }

    public static void beginTransaction() {
        sf.getCurrentSession().beginTransaction();
    }

    public static void commitTransaction() {
        sf.getCurrentSession().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        sf.getCurrentSession().getTransaction().rollback();
    }

    public static void closeCurrentSession() {
        if (sf.getCurrentSession().isOpen()) {
            sf.getCurrentSession().close();
        }
    }
}
