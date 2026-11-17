package com.tuitionfee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entity representing a fee payment transaction made by a student.
 */
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignment_id", nullable = false)
    private FeeAssignment feeAssignment;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Column(name = "payment_date", insertable = false, updatable = false)
    private Timestamp paymentDate;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "transaction_reference", nullable = false, unique = true, length = 80)
    private String transactionReference;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus; // "PAID", "PENDING", "PARTIAL"

    @Column(name = "receipt_number", nullable = false, unique = true, length = 80)
    private String receiptNumber;

    public Payment() {
    }

    public Payment(FeeAssignment feeAssignment, double amountPaid, String paymentMethod,
                   String transactionReference, String paymentStatus, String receiptNumber) {
        this.feeAssignment = feeAssignment;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.paymentStatus = paymentStatus;
        this.receiptNumber = receiptNumber;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public FeeAssignment getFeeAssignment() {
        return feeAssignment;
    }

    public void setFeeAssignment(FeeAssignment feeAssignment) {
        this.feeAssignment = feeAssignment;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
}
