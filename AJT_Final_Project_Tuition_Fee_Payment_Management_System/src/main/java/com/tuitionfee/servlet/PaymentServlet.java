package com.tuitionfee.servlet;

import com.tuitionfee.exception.InvalidPaymentException;
import com.tuitionfee.model.Course;
import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.model.Payment;
import com.tuitionfee.service.CourseService;
import com.tuitionfee.service.FeeService;
import com.tuitionfee.service.PaymentService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling fee payments.
 * Mapped to /admin/payments for admin management and /student/pay for student simulated checkout.
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/admin/payments", "/student/pay"})
public class PaymentServlet extends HttpServlet {

    private PaymentService paymentService;
    private FeeService feeService;
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        this.paymentService = SpringContextHelper.getBean("paymentService", PaymentService.class);
        this.feeService = SpringContextHelper.getBean("feeService", FeeService.class);
        this.courseService = SpringContextHelper.getBean("courseService", CourseService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String servletPath = req.getServletPath();

        if (servletPath.startsWith("/admin")) {
            handleAdminPaymentList(req, resp);
        } else {
            handleStudentPaymentForm(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String servletPath = req.getServletPath();

        if (servletPath.startsWith("/student")) {
            handleStudentPaymentSubmission(req, resp);
        } else {
            // Admin status updates
            String action = req.getParameter("action");
            if ("updateStatus".equals(action)) {
                String paymentIdStr = req.getParameter("paymentId");
                String newStatus = req.getParameter("newStatus");
                if (paymentIdStr != null && newStatus != null) {
                    try {
                        int paymentId = Integer.parseInt(paymentIdStr);
                        paymentService.updatePaymentStatus(paymentId, newStatus);
                        resp.sendRedirect(req.getContextPath() + "/admin/payments?msg=Payment+status+updated+to+" + newStatus + "+successfully.");
                        return;
                    } catch (Exception ignored) {}
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/payments");
        }
    }

    /**
     * Admin view: Lists and filters all payment transactions.
     */
    private void handleAdminPaymentList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String courseIdStr = req.getParameter("courseId");
        String status = req.getParameter("status");
        String keyword = req.getParameter("keyword");

        Integer courseId = null;
        if (courseIdStr != null && !courseIdStr.trim().isEmpty()) {
            try {
                courseId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException ignored) {}
        }

        List<Payment> payments = paymentService.filterPayments(courseId, status, keyword);
        List<Course> courses = courseService.getAllCourses();

        req.setAttribute("payments", payments);
        req.setAttribute("courses", courses);
        req.setAttribute("selectedCourseId", courseId);
        req.setAttribute("selectedStatus", status);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher("/admin/payments.jsp").forward(req, resp);
    }

    /**
     * Student view: Displays the simulated payment checkout confirmation form.
     */
    private void handleStudentPaymentForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String assignmentIdStr = req.getParameter("assignmentId");
        if (assignmentIdStr == null || assignmentIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/student/dashboard?error=Please+select+a+fee+item+to+pay.");
            return;
        }

        int assignmentId = Integer.parseInt(assignmentIdStr);
        FeeAssignment assignment = feeService.getAssignmentById(assignmentId);
        if (assignment == null) {
            resp.sendRedirect(req.getContextPath() + "/student/dashboard?error=Fee+record+not+found.");
            return;
        }

        req.setAttribute("assignment", assignment);
        req.getRequestDispatcher("/student/payment-form.jsp").forward(req, resp);
    }

    /**
     * Student action: Processes the simulated payment transaction.
     */
    private void handleStudentPaymentSubmission(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String assignmentIdStr = req.getParameter("assignmentId");
        String amountStr = req.getParameter("amount");
        String paymentMethod = req.getParameter("paymentMethod");

        if (assignmentIdStr == null || amountStr == null || paymentMethod == null ||
            assignmentIdStr.trim().isEmpty() || amountStr.trim().isEmpty() || paymentMethod.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/student/dashboard?error=Invalid+payment+submission.");
            return;
        }

        try {
            int assignmentId = Integer.parseInt(assignmentIdStr);
            double amount = Double.parseDouble(amountStr);

            Payment payment = paymentService.processSimulatedPayment(assignmentId, amount, paymentMethod);
            // Redirect to receipt display page
            resp.sendRedirect(req.getContextPath() + "/receipt?receiptNo=" + payment.getReceiptNumber() + "&msg=Payment+Processed+Successfully!");
        } catch (InvalidPaymentException e) {
            resp.sendRedirect(req.getContextPath() + "/student/pay?assignmentId=" + assignmentIdStr + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/student/pay?assignmentId=" + assignmentIdStr + "&error=An+unexpected+error+occurred.");
        }
    }
}
