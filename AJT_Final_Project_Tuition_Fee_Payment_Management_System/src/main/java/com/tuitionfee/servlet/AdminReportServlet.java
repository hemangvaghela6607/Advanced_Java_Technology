package com.tuitionfee.servlet;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for Admin Financial Reports.
 * Provides course-wise fee collection, pending balance breakdowns, and aggregate statistics.
 */
@WebServlet(name = "AdminReportServlet", urlPatterns = {"/admin/reports"})
public class AdminReportServlet extends HttpServlet {

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

        double totalCollected = paymentService.calculateTotalCollected();
        double totalPending = paymentService.calculateTotalPending();
        List<Course> courses = courseService.getAllCourses();
        List<FeeAssignment> assignments = feeService.getAllAssignments();
        List<Payment> payments = paymentService.getAllPayments();

        // Calculate course-wise collection metrics
        List<CourseReportItem> reportItems = new ArrayList<>();

        for (Course c : courses) {
            int studentCount = 0;
            double courseAssignedTotal = 0.0;
            double coursePaidTotal = 0.0;

            for (FeeAssignment fa : assignments) {
                if (fa.getStudent().getCourse() != null && 
                    fa.getStudent().getCourse().getCourseId() == c.getCourseId()) {
                    studentCount++;
                    courseAssignedTotal += fa.getTotalFee();
                    coursePaidTotal += fa.getPaidAmount();
                }
            }

            double coursePending = courseAssignedTotal - coursePaidTotal;
            reportItems.add(new CourseReportItem(
                    c.getCourseName(),
                    studentCount,
                    courseAssignedTotal,
                    coursePaidTotal,
                    coursePending > 0 ? coursePending : 0.0
            ));
        }

        req.setAttribute("totalCollected", totalCollected);
        req.setAttribute("totalPending", totalPending);
        req.setAttribute("totalAssigned", totalCollected + totalPending);
        req.setAttribute("reportItems", reportItems);

        req.getRequestDispatcher("/admin/reports.jsp").forward(req, resp);
    }

    /**
     * DTO helper class for rendering course-wise summary reports.
     */
    public static class CourseReportItem {
        private String courseName;
        private int studentCount;
        private double totalAssigned;
        private double totalPaid;
        private double totalPending;

        public CourseReportItem(String courseName, int studentCount, double totalAssigned,
                                double totalPaid, double totalPending) {
            this.courseName = courseName;
            this.studentCount = studentCount;
            this.totalAssigned = totalAssigned;
            this.totalPaid = totalPaid;
            this.totalPending = totalPending;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getStudentCount() {
            return studentCount;
        }

        public double getTotalAssigned() {
            return totalAssigned;
        }

        public double getTotalPaid() {
            return totalPaid;
        }

        public double getTotalPending() {
            return totalPending;
        }
    }
}
