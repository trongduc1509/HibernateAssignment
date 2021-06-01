package hibernate.DAO;

import hibernate.POJO.Subject;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SubjectDAO {
    private static Session session;
    public static List<Subject> getAllSubject() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Subject> subjectList = null;
        try {
            final String hql = "from Subject";
            Query query = session.createQuery(hql);
            subjectList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return subjectList;
    }

    public static List<Subject> searchSubjectById(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Subject> listFindedSubject = null;
        try {
            final String hql = "from Subject where id like '%" + idFind + "%'";
            Query query = session.createQuery(hql);
            listFindedSubject = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return listFindedSubject;
    }

    public static Subject getDeteminedSubject(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Subject> listFindedSubject = null;
        try {
            final String hql = "from Subject where id = '" + idFind + "'";
            Query query = session.createQuery(hql);
            listFindedSubject = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return (listFindedSubject.isEmpty()) ? null : listFindedSubject.get(0);
    }

    public static void add(Subject newSj) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(newSj);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void update(Subject updateSj) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(updateSj);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void delete(Subject delSj) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(delSj);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }
}
