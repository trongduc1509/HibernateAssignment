package hibernate.DAO;

import hibernate.POJO.Person;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PersonDAO {
    private static Session session;
    public static List<Person> getAllUser() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> userList = null;
        try
        {
            final String hql = "from Person ";
            Query query = session.createQuery(hql);
            userList = query.list();
        }
        catch (HibernateException e)
        {
            System.err.println(e);
        }
        finally {
            session.close();
        }
        return userList;
    }

    public static List<Person> getAllTeacher() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> teacherList = null;
        try {
            final String hql = "from Person where role = 'GV'";
            Query query = session.createQuery((hql));
            teacherList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return teacherList;
    }

    public static List<Person> getAllStudent() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> studentList = null;
        try {
            final String hql = "from Person where role = 'SV'";
            Query query = session.createQuery((hql));
            studentList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return studentList;
    }
}
