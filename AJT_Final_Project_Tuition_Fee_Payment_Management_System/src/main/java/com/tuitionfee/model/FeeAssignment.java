package com.tuitionfee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Entity representing a fee allocated to an enrolled student for an academic term/period.
 */
@Entity
@Table(name = "fee_assignments")
public class FeeAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private int assignmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "total_fee", nullable = false)
    private double totalFee;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "academic_period", nullable = false, length = 60)
    private String academicPeriod;

    @Column(name = "remarks", length = 255)
    private String remarks;

    // Transient attributes for UI convenience (not stored directly in fee_assignments table)
    @Transient
    private double paidAmount;

    @Transient
    private double balanceAmount;

    @Transient
    private String paymentStatus; // "PAID", "PARTIAL", "PENDING"

    public FeeAssignment() {
    }

    public FeeAssignment(Student student, double totalFee, Date dueDate, String academicPeriod, String remarks) {
        this.student = student;
        this.totalFee = totalFee;
        this.dueDate = dueDate;
        this.academicPeriod = academicPeriod;
        this.remarks = remarks;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getAcademicPeriod() {
        return academicPeriod;
    }

    public void setAcademicPeriod(String academicPeriod) {
        this.academicPeriod = academicPeriod;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
