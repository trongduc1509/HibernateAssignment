package hibernate.DAO;

import hibernate.POJO.Clazz;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ClazzDAO {
    private static Session session;
    public static List<Clazz> getAllClass() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Clazz> allClass = null;
        try {
            final String hql = "from Clazz";
            Query query = session.createQuery(hql);
            allClass = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return allClass;
    }

    public static Clazz getDeterminedClass(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Clazz> allClass = null;
        try {
            final String hql = "from Clazz where id = '" + idFind + "'";
            Query query = session.createQuery(hql);
            allClass = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return (allClass.isEmpty()) ? null : allClass.get(0);
    }

    public static List<Clazz> searchClassById(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Clazz> allClass = null;
        try {
            final String hql = "from Clazz where id like '%" + idFind + "%'";
            Query query = session.createQuery(hql);
            allClass = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return allClass;
    }

    public static void add(Clazz temp) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(temp);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void delete(Clazz temp) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(temp);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void update(Clazz temp) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(temp);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }
}
