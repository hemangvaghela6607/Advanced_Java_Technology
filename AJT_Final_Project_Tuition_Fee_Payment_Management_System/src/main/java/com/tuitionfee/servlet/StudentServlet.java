package com.tuitionfee.servlet;

import com.tuitionfee.model.Course;
import com.tuitionfee.model.Student;
import com.tuitionfee.service.CourseService;
import com.tuitionfee.service.StudentService;
import com.tuitionfee.service.UserService;
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
 * Controller for Admin Student Management (CRUD operations).
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/admin/students"})
public class StudentServlet extends HttpServlet {

    private StudentService studentService;
    private CourseService courseService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.studentService = SpringContextHelper.getBean("studentService", StudentService.class);
        this.courseService = SpringContextHelper.getBean("courseService", CourseService.class);
        this.userService = SpringContextHelper.getBean("userService", UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteStudent(req, resp);
                break;
            default:
                listStudents(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String studentIdStr = req.getParameter("studentId");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String rollNumber = req.getParameter("rollNumber");
        String courseIdStr = req.getParameter("courseId");
        String address = req.getParameter("address");
        String admissionDateStr = req.getParameter("admissionDate");
        String password = req.getParameter("password");

        // Validate basic fields
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            rollNumber == null || rollNumber.trim().isEmpty() ||
            courseIdStr == null || courseIdStr.trim().isEmpty()) {
            
            req.setAttribute("errorMessage", "Name, Email, Roll Number, and Course are required.");
            if (studentIdStr != null && !studentIdStr.isEmpty()) {
                showEditForm(req, resp);
            } else {
                showNewForm(req, resp);
            }
            return;
        }

        int courseId = Integer.parseInt(courseIdStr);
        Date admissionDate;
        try {
            admissionDate = (admissionDateStr != null && !admissionDateStr.isEmpty())
                    ? Date.valueOf(admissionDateStr)
                    : new Date(System.currentTimeMillis());
        } catch (IllegalArgumentException e) {
            admissionDate = new Date(System.currentTimeMillis());
        }

        if (studentIdStr != null && !studentIdStr.trim().isEmpty()) {
            // Update student
            int studentId = Integer.parseInt(studentIdStr);
            boolean updated = studentService.updateStudent(
                    studentId, name.trim(), email.trim(), phone, rollNumber.trim(),
                    courseId, address, admissionDate
            );
            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/admin/students?msg=Student+updated+successfully.");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/students?error=Failed+to+update+student.");
            }
        } else {
            // New student
            if (password == null || password.trim().isEmpty()) {
                password = "student123"; // default temporary password
            }
            int newId = studentService.createStudentWithAccount(
                    name.trim(), email.trim(), password.trim(), phone, rollNumber.trim(),
                    courseId, address, admissionDate
            );
            if (newId > 0) {
                resp.sendRedirect(req.getContextPath() + "/admin/students?msg=Student+added+successfully.");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/students?error=Failed+to+create+student.+Email+or+Roll+Number+might+already+exist.");
            }
        }
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Student> list = studentService.getAllStudents();
        req.setAttribute("students", list);
        req.getRequestDispatcher("/admin/students.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Course> courses = courseService.getAllCourses();
        req.setAttribute("courses", courses);
        req.setAttribute("generatedRollNumber", studentService.generateUniqueRollNumber());
        req.getRequestDispatcher("/admin/student-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int studentId = Integer.parseInt(req.getParameter("id"));
        Student student = studentService.getStudentById(studentId);
        List<Course> courses = courseService.getAllCourses();
        req.setAttribute("student", student);
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/admin/student-form.jsp").forward(req, resp);
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int studentId = Integer.parseInt(req.getParameter("id"));
        boolean deleted = studentService.deleteStudent(studentId);
        if (deleted) {
            resp.sendRedirect(req.getContextPath() + "/admin/students?msg=Student+deleted+successfully.");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/students?error=Could+not+delete+student.");
        }
    }
}
