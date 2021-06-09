package hibernate.DAO;

import hibernate.POJO.RegistSubject;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RegistSubjectDAO {
    private static Session session;
    public static List<RegistSubject> getListByCourseId(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<RegistSubject> resList = null;
        try {
            final String hql = "from RegistSubject where courseId = " + idFind;
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static int countCurrentSlotInCourse(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        int res = 0;
        try {
            final String hql = "select count(*) from RegistSubject where courseId = " + idFind;
            Query query = session.createQuery(hql);
            res = ((Long)query.getSingleResult()).intValue();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return res;
    }

    public static void add(RegistSubject temp) {
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

    public static void delete(RegistSubject temp) {
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
