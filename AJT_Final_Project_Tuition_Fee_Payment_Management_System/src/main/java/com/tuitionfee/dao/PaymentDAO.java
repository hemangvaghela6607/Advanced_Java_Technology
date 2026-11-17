package com.tuitionfee.dao;

import com.tuitionfee.model.Payment;
import java.util.List;

/**
 * Data Access Object interface for Payment operations using Hibernate.
 */
public interface PaymentDAO {
    Payment findById(int paymentId);
    Payment findByReceiptNumber(String receiptNumber);
    Payment findByTransactionReference(String txnRef);
    List<Payment> findByAssignmentId(int assignmentId);
    List<Payment> findByStudentId(int studentId);
    List<Payment> findAll();
    List<Payment> filterPayments(Integer courseId, String status, String searchKeyword);
    int save(Payment payment);
    boolean update(Payment payment);
    boolean delete(int paymentId);
}
