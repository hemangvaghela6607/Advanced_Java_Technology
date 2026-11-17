package com.tuitionfee.servlet;

import com.tuitionfee.model.Course;
import com.tuitionfee.model.Payment;
import com.tuitionfee.model.Student;
import com.tuitionfee.service.CourseService;
import com.tuitionfee.service.FeeService;
import com.tuitionfee.service.PaymentService;
import com.tuitionfee.service.StudentService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Admin Dashboard analytics and summary metrics.
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private StudentService studentService;
    private CourseService courseService;
    private FeeService feeService;
    private PaymentService paymentService;

    @Override
    public void init() throws ServletException {
        this.studentService = SpringContextHelper.getBean("studentService", StudentService.class);
        this.courseService = SpringContextHelper.getBean("courseService", CourseService.class);
        this.feeService = SpringContextHelper.getBean("feeService", FeeService.class);
        this.paymentService = SpringContextHelper.getBean("paymentService", PaymentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Student> students = studentService.getAllStudents();
        List<Course> courses = courseService.getAllCourses();
        List<Payment> payments = paymentService.getAllPayments();

        double totalCollected = paymentService.calculateTotalCollected();
        double totalPending = paymentService.calculateTotalPending();

        // Pass metrics to admin/dashboard.jsp
        req.setAttribute("totalStudents", students.size());
        req.setAttribute("totalCourses", courses.size());
        req.setAttribute("totalCollected", totalCollected);
        req.setAttribute("totalPending", totalPending);
        req.setAttribute("recentPayments", payments.size() > 5 ? payments.subList(0, 5) : payments);

        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}
