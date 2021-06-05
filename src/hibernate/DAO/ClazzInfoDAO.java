package hibernate.DAO;

import hibernate.POJO.ClazzInfo;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ClazzInfoDAO {
    private static Session session;
    public static List<ClazzInfo> getAllInfoByClazz(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ClazzInfo> searchList = null;
        try {
            final String hql = "from ClazzInfo where classId = '" + idFind + "'";
            Query query = session.createQuery(hql);
            searchList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return searchList;
    }

    public static void add(ClazzInfo temp) {
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
    public static void delete(ClazzInfo temp) {
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
}
