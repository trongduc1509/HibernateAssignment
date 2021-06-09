package hibernate.DAO;

import hibernate.POJO.Course;
import hibernate.POJO.Person;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDAO {
    private static Session session;
    public static void add(Course temp) {
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

    public static void delete(Course temp) {
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

    public static Course getDeteminedCourse(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "from Course where courseId = " + idFind;
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList.get(0);
    }

    public static List<Course> getAllCourseBySemester(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "from Course where semesterId = " + idFind;
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static List<Course> getAllCourseBySbj(String subj) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "from Course where subjectId = '" + subj + "'";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static List<Course> getAllCourseRegistedByStudent(String idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "select c from Course c, RegistSubject rs where rs.courseId = c.courseId " +
                    "and rs.student = '" + idFind + "'";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static List<Course> getAllCourseRegistedByStudentAndSem(String idFind, Integer idSem) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "select c from Course c, RegistSubject rs where rs.courseId = c.courseId " +
                    "and c.semesterId = " + idSem + " and rs.student = '" + idFind + "'";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static List<Course> getAllCourseBySemesterAndSbj(Integer idFind, String subj) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> resList = null;
        try {
            final String hql = "from Course where semesterId = " + idFind + " "
                    + "and subjectId like '%" + subj + "%'";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }

    public static List<Person> stuInCourse(Integer idFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Person> resList = null;
        try {
            final String hql = "select p from Person p, RegistSubject rs where rs.courseId = " + idFind + " "
                    + "and p.id = rs.student";
            Query query = session.createQuery(hql);
            resList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return resList;
    }
}
