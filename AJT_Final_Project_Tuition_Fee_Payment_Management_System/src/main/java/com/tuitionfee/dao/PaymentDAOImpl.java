package com.tuitionfee.dao;

import com.tuitionfee.model.Payment;
import com.tuitionfee.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Hibernate-based implementation of PaymentDAO.
 * Manages student payments, transactions, receipts, and reporting filters.
 */
public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public Payment findById(int paymentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Payment.class, paymentId);
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findById] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Payment findByReceiptNumber(String receiptNumber) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Payment> query = session.createQuery(
                "FROM Payment p WHERE p.receiptNumber = :rec", Payment.class);
            query.setParameter("rec", receiptNumber.trim());
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findByReceiptNumber] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Payment findByTransactionReference(String txnRef) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Payment> query = session.createQuery(
                "FROM Payment p WHERE p.transactionReference = :ref", Payment.class);
            query.setParameter("ref", txnRef.trim());
            return query.uniqueResult();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findByTransactionReference] Error: " + e.getMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> findByAssignmentId(int assignmentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Payment> query = session.createQuery(
                "FROM Payment p WHERE p.feeAssignment.assignmentId = :aid ORDER BY p.paymentDate DESC", 
                Payment.class);
            query.setParameter("aid", assignmentId);
            return query.list();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findByAssignmentId] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> findByStudentId(int studentId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Payment> query = session.createQuery(
                "FROM Payment p WHERE p.feeAssignment.student.studentId = :sid ORDER BY p.paymentDate DESC", 
                Payment.class);
            query.setParameter("sid", studentId);
            return query.list();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findByStudentId] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Payment> query = session.createQuery(
                "FROM Payment p ORDER BY p.paymentDate DESC", Payment.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.findAll] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> filterPayments(Integer courseId, String status, String searchKeyword) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            StringBuilder hql = new StringBuilder("SELECT p FROM Payment p WHERE 1=1 ");

            if (courseId != null && courseId > 0) {
                hql.append(" AND p.feeAssignment.student.course.courseId = :cid");
            }
            if (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) {
                hql.append(" AND p.paymentStatus = :status");
            }
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                hql.append(" AND (LOWER(p.feeAssignment.student.rollNumber) LIKE :kw " +
                           " OR LOWER(p.feeAssignment.student.user.name) LIKE :kw " +
                           " OR LOWER(p.receiptNumber) LIKE :kw " +
                           " OR LOWER(p.transactionReference) LIKE :kw)");
            }

            hql.append(" ORDER BY p.paymentDate DESC");

            Query<Payment> query = session.createQuery(hql.toString(), Payment.class);

            if (courseId != null && courseId > 0) {
                query.setParameter("cid", courseId);
            }
            if (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) {
                query.setParameter("status", status.trim());
            }
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                query.setParameter("kw", "%" + searchKeyword.trim().toLowerCase() + "%");
            }

            return query.list();
        } catch (Exception e) {
            System.err.println("[PaymentDAOImpl.filterPayments] Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public int save(Payment payment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(payment);
            tx.commit();
            return payment.getPaymentId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[PaymentDAOImpl.save] Error: " + e.getMessage());
            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Payment payment) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(payment);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[PaymentDAOImpl.update] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(int paymentId) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Payment p = session.get(Payment.class, paymentId);
            if (p != null) {
                session.delete(p);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("[PaymentDAOImpl.delete] Error: " + e.getMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
