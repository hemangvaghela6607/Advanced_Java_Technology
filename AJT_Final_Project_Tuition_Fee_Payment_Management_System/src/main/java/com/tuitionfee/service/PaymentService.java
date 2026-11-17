package com.tuitionfee.service;

import com.tuitionfee.dao.FeeAssignmentDAO;
import com.tuitionfee.dao.PaymentDAO;
import com.tuitionfee.exception.InvalidPaymentException;
import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.model.Payment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Service class handling payment transactions, simulated checkouts, and financial reporting.
 * Managed by Spring IoC Container via Dependency Injection.
 */
public class PaymentService {

    // Spring Injected DAOs
    private PaymentDAO paymentDAO;
    private FeeAssignmentDAO feeAssignmentDAO;

    public PaymentService() {
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public void setFeeAssignmentDAO(FeeAssignmentDAO feeAssignmentDAO) {
        this.feeAssignmentDAO = feeAssignmentDAO;
    }

    /**
     * Executes a simulated payment transaction.
     * Validates that payment amount is positive and does not exceed the remaining balance.
     */
    public Payment processSimulatedPayment(int assignmentId, double amountToPay, String paymentMethod) {
        if (amountToPay <= 0) {
            throw new InvalidPaymentException("Payment amount must be greater than zero.");
        }

        FeeAssignment assignment = feeAssignmentDAO.findById(assignmentId);
        if (assignment == null) {
            throw new InvalidPaymentException("Fee assignment record not found.");
        }

        // Calculate already paid amount
        List<Payment> existingPayments = paymentDAO.findByAssignmentId(assignmentId);
        double alreadyPaid = 0.0;
        for (Payment p : existingPayments) {
            alreadyPaid += p.getAmountPaid();
        }

        double remainingBalance = assignment.getTotalFee() - alreadyPaid;
        // Allow a small epsilon for floating-point comparisons
        if (amountToPay > (remainingBalance + 0.01)) {
            throw new InvalidPaymentException(String.format(
                "Payment amount (₹%.2f) exceeds outstanding balance (₹%.2f).", 
                amountToPay, remainingBalance));
        }

        // Determine payment status
        String status = (amountToPay >= remainingBalance - 0.01) ? "PAID" : "PARTIAL";

        // Generate unique transaction reference and receipt number
        String txnRef = generateTransactionReference();
        String receiptNo = generateReceiptNumber();

        // Create and persist payment record
        Payment payment = new Payment(assignment, amountToPay, paymentMethod, txnRef, status, receiptNo);
        int paymentId = paymentDAO.save(payment);
        if (paymentId > 0) {
            payment.setPaymentId(paymentId);
            return payment;
        } else {
            throw new InvalidPaymentException("Failed to record payment in database.");
        }
    }

    public Payment getPaymentById(int paymentId) {
        return paymentDAO.findById(paymentId);
    }

    public Payment getPaymentByReceiptNumber(String receiptNumber) {
        if (receiptNumber == null || receiptNumber.trim().isEmpty()) {
            return null;
        }
        return paymentDAO.findByReceiptNumber(receiptNumber.trim());
    }

    public List<Payment> getPaymentsByStudentId(int studentId) {
        return paymentDAO.findByStudentId(studentId);
    }

    public List<Payment> getPaymentsByAssignmentId(int assignmentId) {
        return paymentDAO.findByAssignmentId(assignmentId);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    public List<Payment> filterPayments(Integer courseId, String status, String searchKeyword) {
        return paymentDAO.filterPayments(courseId, status, searchKeyword);
    }

    /**
     * Updates the status of an existing payment record (e.g. from PARTIAL to PAID).
     */
    public boolean updatePaymentStatus(int paymentId, String newStatus) {
        Payment payment = paymentDAO.findById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus(newStatus);
            return paymentDAO.update(payment);
        }
        return false;
    }

    public double calculateTotalCollected() {
        List<Payment> all = paymentDAO.findAll();
        double sum = 0.0;
        for (Payment p : all) {
            sum += p.getAmountPaid();
        }
        return sum;
    }

    public double calculateTotalPending() {
        List<FeeAssignment> assignments = feeAssignmentDAO.findAll();
        double totalAssigned = 0.0;
        for (FeeAssignment fa : assignments) {
            totalAssigned += fa.getTotalFee();
        }
        double totalCollected = calculateTotalCollected();
        double pending = totalAssigned - totalCollected;
        return pending > 0 ? pending : 0.0;
    }

    private String generateTransactionReference() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        int randomNum = 1000 + new Random().nextInt(9000);
        return "TXN-" + dateStr + "-" + randomNum;
    }

    private String generateReceiptNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String yearStr = sdf.format(new Date());
        int randomNum = 1000 + new Random().nextInt(9000);
        return "REC-" + yearStr + "-" + randomNum;
    }
}
