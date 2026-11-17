package com.tuitionfee.servlet;

import com.tuitionfee.model.FeeAssignment;
import com.tuitionfee.model.Student;
import com.tuitionfee.service.FeeService;
import com.tuitionfee.service.StudentService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Controller for Admin Fee Assignment Management.
 * Allows allocating fees, due dates, and academic periods to enrolled students.
 */
@WebServlet(name = "FeeAssignmentServlet", urlPatterns = {"/admin/fee-assignments"})
public class FeeAssignmentServlet extends HttpServlet {

    private FeeService feeService;
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        this.feeService = SpringContextHelper.getBean("feeService", FeeService.class);
        this.studentService = SpringContextHelper.getBean("studentService", StudentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            try {
                int assignmentId = Integer.parseInt(req.getParameter("id"));
                boolean deleted = feeService.deleteAssignment(assignmentId);
                if (deleted) {
                    resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?msg=Fee+assignment+deleted+successfully.");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Could+not+delete+assignment.+Payment+records+may+exist.");
                }
                return;
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Invalid+assignment+ID.");
                return;
            }
        }

        List<FeeAssignment> assignments = feeService.getAllAssignments();
        List<Student> students = studentService.getAllStudents();

        req.setAttribute("assignments", assignments);
        req.setAttribute("students", students);

        req.getRequestDispatcher("/admin/fee-assignments.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String studentIdStr = req.getParameter("studentId");
        String totalFeeStr = req.getParameter("totalFee");
        String dueDateStr = req.getParameter("dueDate");
        String academicPeriod = req.getParameter("academicPeriod");
        String remarks = req.getParameter("remarks");

        // Validation
        if (studentIdStr == null || totalFeeStr == null || dueDateStr == null || academicPeriod == null ||
            studentIdStr.trim().isEmpty() || totalFeeStr.trim().isEmpty() || 
            dueDateStr.trim().isEmpty() || academicPeriod.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Student,+Total+Fee,+Due+Date,+and+Period+are+required.");
            return;
        }

        try {
            int studentId = Integer.parseInt(studentIdStr);
            double totalFee = Double.parseDouble(totalFeeStr);
            Date dueDate = Date.valueOf(dueDateStr);

            if (totalFee <= 0) {
                resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Fee+amount+must+be+greater+than+zero.");
                return;
            }

            int id = feeService.assignFee(studentId, totalFee, dueDate, academicPeriod.trim(), remarks);
            if (id > 0) {
                resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?msg=Fee+assigned+successfully.");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Failed+to+assign+fee.");
            }
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/fee-assignments?error=Invalid+number+or+date+format.");
        }
    }
}
