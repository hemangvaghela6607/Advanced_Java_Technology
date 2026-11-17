package com.tuitionfee.dao;

import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Hibernate-based implementation of FeeAssignmentDAO.
 * Manages student fee assignments, due dates, and academic periods.
 */
public class FeeAssignmentDAOImpl implements FeeAssignmentDAO {

    @Override
    public FeeAssignment findById(int assignmentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(FeeAssignment.class, assignmentId);
        } catch (Exception e) {
            System.err.println("[FeeAssignmentDAOImpl.findById] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<FeeAssignment> findByStudentId(int studentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<FeeAssignment> query = session.createQuery(
                "FROM FeeAssignment f WHERE f.student.studentId = :sid ORDER BY f.dueDate ASC", 
                FeeAssignment.class);
            query.setParameter("sid", studentId);
            return query.list();
        } catch (Exception e) {
            System.err.println("[FeeAssignmentDAOImpl.findByStudentId] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<FeeAssignment> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<FeeAssignment> query = session.createQuery(
                "FROM FeeAssignment f ORDER BY f.assignmentId DESC", FeeAssignment.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("[FeeAssignmentDAOImpl.findAll] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public int save(FeeAssignment feeAssignment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(feeAssignment);
            tx.commit();
            return feeAssignment.getAssignmentId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[FeeAssignmentDAOImpl.save] Error: " + e.getMessage());
            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(FeeAssignment feeAssignment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(feeAssignment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[FeeAssignmentDAOImpl.update] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(int assignmentId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            FeeAssignment fa = session.get(FeeAssignment.class, assignmentId);
            if (fa != null) {
                session.delete(fa);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[FeeAssignmentDAOImpl.delete] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
