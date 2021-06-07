package hibernate.DAO;

import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SessionDAO {
    private static Session session;

    public static List<hibernate.POJO.Session> getAllSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<hibernate.POJO.Session> resList = null;
        try {
            final String hql = "from Session";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static hibernate.POJO.Session getDeterminedSession(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<hibernate.POJO.Session> resList = null;
        try {
            final String hql = "from Session where id = " + idFind;
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList.get(0);
    }
}
