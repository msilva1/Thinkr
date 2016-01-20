/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.sandbox;

import java.util.List;
import java.util.Iterator;

import com.bytes.thinkr.model.util.HibernateUtil;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.factory.AccountEntityFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class LocalDBTest {

    @Test
    public void createAccounts() {

        List<Account> accounts = AccountEntityFactory.getInstance().generate(1000);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 0;
            for (Account account : accounts) {
                account.setId((Long) session.save(account));
                if (i++ % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        listAccount();
    }

    public void listAccount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List accounts = session.createQuery("from Account").list();
            for (Iterator iterator = accounts.iterator(); iterator.hasNext(); ) {
                Account account = (Account) iterator.next();
                System.out.println(account.getClient().getDisplayName());
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