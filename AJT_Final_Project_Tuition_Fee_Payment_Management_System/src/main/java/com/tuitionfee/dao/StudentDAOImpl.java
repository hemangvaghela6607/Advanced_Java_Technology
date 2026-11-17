package com.tuitionfee.dao;

import com.tuitionfee.model.Student;
import com.tuitionfee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Hibernate-based implementation of StudentDAO.
 * Demonstrates Session, Transaction, and HQL (Hibernate Query Language) operations.
 */
public class StudentDAOImpl implements StudentDAO {

    @Override
    public Student findById(int studentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Student.class, studentId);
        } catch (Exception e) {
            System.err.println("[StudentDAOImpl.findById] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Student findByUserId(int userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery(
                "FROM Student s WHERE s.user.userId = :uid", Student.class);
            query.setParameter("uid", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("[StudentDAOImpl.findByUserId] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Student findByRollNumber(String rollNumber) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery(
                "FROM Student s WHERE s.rollNumber = :roll", Student.class);
            query.setParameter("roll", rollNumber);
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("[StudentDAOImpl.findByRollNumber] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Student> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Student> query = session.createQuery("FROM Student ORDER BY studentId ASC", Student.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("[StudentDAOImpl.findAll] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public int save(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
            return student.getStudentId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[StudentDAOImpl.save] Error: " + e.getMessage());
            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(student);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[StudentDAOImpl.update] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(int studentId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[StudentDAOImpl.delete] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
