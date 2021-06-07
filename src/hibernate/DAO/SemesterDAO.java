package hibernate.DAO;

import hibernate.POJO.Semester;
import hibernate.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SemesterDAO {
    private static Session session;
    public static List<Semester> getAllSemester() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semList = null;
        try {
            final String hql = "from Semester";
            Query query = session.createQuery(hql);
            semList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return semList;
    }

    public static List<Semester> getSemesterByYear(int yearFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semSearchList = null;
        try {
            final String hql = "from Semester where year = " + yearFind;
            Query query = session.createQuery(hql);
            semSearchList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return semSearchList;
    }

    public static Semester getDeterminedSemester(String nameFind, int yearFind) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semSearchList = null;
        try {
            final String hql = "from Semester where year = " + yearFind + " and name = '" + nameFind + "'";
            Query query = session.createQuery(hql);
            semSearchList = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return (semSearchList.isEmpty()) ? null : semSearchList.get(0);
    }

    public static void add(Semester newS) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(newS);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void delete(Semester delS) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(delS);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
    }

    public static void saveCurrentSemester(Semester curSem) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("currentSemester.dat"));
        fos.write(curSem.getId());
        fos.close();
    }

    public static int loadCurrentSemester() throws IOException {
        File f = new File("currentSemester.dat");
        int res = -1;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            res = fis.read();
        }
        return res;
    }
}
