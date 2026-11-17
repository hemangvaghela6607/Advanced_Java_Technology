package com.tuitionfee.service;

import com.tuitionfee.dao.FeeAssignmentDAO;
import com.tuitionfee.dao.PaymentDAO;
import com.tuitionfee.dao.StudentDAO;
import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.model.Payment;
import com.tuitionfee.model.Student;

import java.sql.Date;
import java.util.List;

/**
 * Service class handling fee assignment to students and balance calculations.
 * Managed by Spring IoC Container via Dependency Injection.
 */
public class FeeService {

    // Spring Injected DAOs
    private FeeAssignmentDAO feeAssignmentDAO;
    private StudentDAO studentDAO;
    private PaymentDAO paymentDAO;

    public FeeService() {
    }

    public void setFeeAssignmentDAO(FeeAssignmentDAO feeAssignmentDAO) {
        this.feeAssignmentDAO = feeAssignmentDAO;
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public FeeAssignment getAssignmentById(int assignmentId) {
        FeeAssignment fa = feeAssignmentDAO.findById(assignmentId);
        if (fa != null) {
            calculateFinancialStatus(fa);
        }
        return fa;
    }

    public List<FeeAssignment> getAssignmentsByStudentId(int studentId) {
        List<FeeAssignment> list = feeAssignmentDAO.findByStudentId(studentId);
        for (FeeAssignment fa : list) {
            calculateFinancialStatus(fa);
        }
        return list;
    }

    public List<FeeAssignment> getAllAssignments() {
        List<FeeAssignment> list = feeAssignmentDAO.findAll();
        for (FeeAssignment fa : list) {
            calculateFinancialStatus(fa);
        }
        return list;
    }

    public int assignFee(int studentId, double totalFee, Date dueDate, String academicPeriod, String remarks) {
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            return -1;
        }
        FeeAssignment fa = new FeeAssignment(student, totalFee, dueDate, academicPeriod, remarks);
        return feeAssignmentDAO.save(fa);
    }

    public boolean updateAssignment(int assignmentId, double totalFee, Date dueDate, String academicPeriod, String remarks) {
        FeeAssignment fa = feeAssignmentDAO.findById(assignmentId);
        if (fa == null) {
            return false;
        }
        fa.setTotalFee(totalFee);
        fa.setDueDate(dueDate);
        fa.setAcademicPeriod(academicPeriod);
        fa.setRemarks(remarks);
        return feeAssignmentDAO.update(fa);
    }

    public boolean deleteAssignment(int assignmentId) {
        return feeAssignmentDAO.delete(assignmentId);
    }

    /**
     * Calculates paid amount, balance amount, and payment status for a fee assignment.
     */
    public void calculateFinancialStatus(FeeAssignment fa) {
        if (fa == null) return;
        double paid = 0.0;
        if (paymentDAO != null) {
            List<Payment> payments = paymentDAO.findByAssignmentId(fa.getAssignmentId());
            for (Payment p : payments) {
                paid += p.getAmountPaid();
            }
        }
        fa.setPaidAmount(paid);
        double balance = fa.getTotalFee() - paid;
        fa.setBalanceAmount(balance > 0 ? balance : 0.0);

        if (paid >= fa.getTotalFee()) {
            fa.setPaymentStatus("PAID");
        } else if (paid > 0) {
            fa.setPaymentStatus("PARTIAL");
        } else {
            fa.setPaymentStatus("PENDING");
        }
    }
}
