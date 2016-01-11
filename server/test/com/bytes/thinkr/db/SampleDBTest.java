package com.bytes.thinkr.db;

import java.util.List;
import java.util.Iterator;

import com.bytes.thinkr.model.Test;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SampleDBTest {

    public static void main(String[] args) {

        SampleDBTest obj = new SampleDBTest();
        Long accountId1 = obj.saveAccount(new User("Nadia"));
        Long accountId2 = obj.saveAccount(new User("Kydan"));
        Long accountId3 = obj.saveAccount(new User("Kaelyn"));

//        Long l1 = obj.saveTest("Kent", "Lam1");
//        Long l2 = obj.saveTest("Kent", "Lam2");
//        Long l3 = obj.saveTest("Kent", "Lam3");
//        Long l4 = obj.saveTest("Kent", "Lam4");

        obj.listAccount();
    }

    public Long saveTest(String f, String l) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        Long result = null;
        try {
            transaction = session.beginTransaction();
            Test t = new Test();
            t.setFirstname(f);
            t.setLastname(l);
            result = (Long) session.save(t);
            t.setId(result);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public Long saveAccount(User user) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long courseId = null;
        try {
            transaction = session.beginTransaction();
            Account account = new Account(user);
            account.setId((Long) session.save(account));
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courseId;
    }

    public void listAccount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List accounts = session.createQuery("from Account").list();
            for (Iterator iterator = accounts.iterator(); iterator.hasNext(); ) {
                Account account = (Account) iterator.next();
                System.out.println(account.getUser().getUserId());
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateCourse(Long courseId, String courseName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            Course course = (Course) session.get(Course.class, courseId);
//            course.setCourseName(courseName);
//            transaction.commit();
//        } catch (HibernateException e) {
//            transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
    }

    public void deleteCourse(Long courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            Course course = (Course) session.get(Course.class, courseId);
//            session.delete(course);
//            transaction.commit();
//        } catch (HibernateException e) {
//            transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
    }

}
