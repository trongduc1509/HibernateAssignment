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

    public static void add(Person user) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void delete(Person user) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void update(Person user) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static List<Person> searchTeacherById(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> listFindedTeacher = null;
        try {
            final String hql = "from Person where role='GV' and id LIKE '%" + idFind + "%'";
            Query query = session.createQuery(hql);
            listFindedTeacher = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return listFindedTeacher;
    }

    public static Person searchSingleTeacherById(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> listFindedTch = null;
        try {
            final String hql = "from Person where role='GV' and id = '" + idFind + "'";
            Query query = session.createQuery(hql);
            listFindedTch = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return (listFindedTch.isEmpty()) ? null : listFindedTch.get(0);
    }

    public static Person searchSingleStudentById(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> listFindedStu = null;
        try {
            final String hql = "from Person where role='SV' and id = '" + idFind + "'";
            Query query = session.createQuery(hql);
            listFindedStu = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return (listFindedStu.isEmpty()) ? null : listFindedStu.get(0);
    }

    public static List<Person> getAllStuInClass(String idClazz) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> listFindedStu = null;
        try {
            final String hql = "select p from Person p, ClazzInfo ci where p.role='SV' " +
                    "and ci.classId = '" + idClazz +
                    "' and p.id = ci.studentId";
            Query query = session.createQuery(hql);
            listFindedStu = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return listFindedStu;
    }

    public static List<Person> searchStuInClass(String idClazz, String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> listFindedStu = null;
        try {
            final String hql = "select p from Person p, ClazzInfo ci where p.role='SV' " +
                    "and ci.classId = '" + idClazz +
                    "' and p.id = ci.studentId" +
                    " and ci.studentId like '%" + idFind + "%'";
            Query query = session.createQuery(hql);
            listFindedStu = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return listFindedStu;
    }
}
