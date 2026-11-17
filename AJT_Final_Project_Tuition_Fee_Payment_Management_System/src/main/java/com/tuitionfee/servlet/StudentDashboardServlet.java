package com.tuitionfee.servlet;

import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.model.Payment;
import com.tuitionfee.model.Student;
import com.tuitionfee.model.User;
import com.tuitionfee.service.FeeService;
import com.tuitionfee.service.PaymentService;
import com.tuitionfee.service.StudentService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Student Dashboard displaying profile summary, fee status, and recent activity.
 */
@WebServlet(name = "StudentDashboardServlet", urlPatterns = {"/student/dashboard"})
public class StudentDashboardServlet extends HttpServlet {

    private StudentService studentService;
    private FeeService feeService;
    private PaymentService paymentService;

    @Override
    public void init() throws ServletException {
        this.studentService = SpringContextHelper.getBean("studentService", StudentService.class);
        this.feeService = SpringContextHelper.getBean("feeService", FeeService.class);
        this.paymentService = SpringContextHelper.getBean("paymentService", PaymentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        Student student = studentService.getStudentByUserId(loggedInUser.getUserId());
        if (student == null) {
            req.setAttribute("errorMessage", "Student profile not found. Please contact administration.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        List<FeeAssignment> assignments = feeService.getAssignmentsByStudentId(student.getStudentId());
        List<Payment> payments = paymentService.getPaymentsByStudentId(student.getStudentId());

        double totalFee = 0.0;
        double totalPaid = 0.0;
        double totalBalance = 0.0;

        for (FeeAssignment fa : assignments) {
            totalFee += fa.getTotalFee();
            totalPaid += fa.getPaidAmount();
            totalBalance += fa.getBalanceAmount();
        }

        req.setAttribute("student", student);
        req.setAttribute("assignments", assignments);
        req.setAttribute("payments", payments);
        req.setAttribute("totalFee", totalFee);
        req.setAttribute("totalPaid", totalPaid);
        req.setAttribute("totalBalance", totalBalance);

        req.getRequestDispatcher("/student/dashboard.jsp").forward(req, resp);
    }
}
