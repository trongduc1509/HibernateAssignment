package hibernate.DAO;

import hibernate.POJO.Crs;
import hibernate.POJO.CrsView;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CrsDAO {
    private static Session session;
    public static List<Crs> getAllCrsBySemester(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Crs> findedList = null;
        try {
            final String hql = "from Crs crs where crs.semesterId = " + idFind;
            Query query = session.createQuery(hql);
            findedList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return findedList;
    }

    public static List<Crs> searchAllCrsBySemName(String nameFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Crs> findedList = null;
        try {
            final String hql = "select crs from Crs crs, Semester se where crs.semesterId = se.id " +
                    "and se.name = '" + nameFind + "'";
            Query query = session.createQuery(hql);
            findedList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return findedList;
    }

    public static boolean checkCanRegist(Integer semId) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Crs> checkList = null;
        try {
            final String hql = "from Crs cr where cr.semesterId = " + semId + " " +
                    "and current_date() between cr.start and cr.end";
            Query query = session.createQuery(hql);
            checkList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        if (checkList.isEmpty())
            return false;
        return true;
    }

    public static void add(Crs temp) {
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

    public static void delete(Crs temp) {
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
    public static void update(Crs temp) {
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
