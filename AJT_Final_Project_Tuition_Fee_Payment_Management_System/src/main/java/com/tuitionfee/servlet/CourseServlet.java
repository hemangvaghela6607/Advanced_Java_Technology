package com.tuitionfee.servlet;

import com.tuitionfee.model.Course;
import com.tuitionfee.service.CourseService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Admin Course Management (Add, Edit, Delete, List).
 */
@WebServlet(name = "CourseServlet", urlPatterns = {"/admin/courses"})
public class CourseServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        this.courseService = SpringContextHelper.getBean("courseService", CourseService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            try {
                int courseId = Integer.parseInt(req.getParameter("id"));
                boolean deleted = courseService.deleteCourse(courseId);
                if (deleted) {
                    resp.sendRedirect(req.getContextPath() + "/admin/courses?msg=Course+deleted+successfully.");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin/courses?error=Unable+to+delete+course.+It+may+have+enrolled+students.");
                }
                return;
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/courses?error=Invalid+course+id.");
                return;
            }
        }

        List<Course> courses = courseService.getAllCourses();
        req.setAttribute("courses", courses);

        String editIdStr = req.getParameter("editId");
        if (editIdStr != null && !editIdStr.trim().isEmpty()) {
            try {
                int editId = Integer.parseInt(editIdStr);
                Course editCourse = courseService.getCourseById(editId);
                req.setAttribute("editCourse", editCourse);
            } catch (Exception ignored) {}
        }

        req.getRequestDispatcher("/admin/courses.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String courseIdStr = req.getParameter("courseId");
        String courseName = req.getParameter("courseName");
        String duration = req.getParameter("duration");
        String totalFeeStr = req.getParameter("totalFee");
        String description = req.getParameter("description");

        if (courseName == null || courseName.trim().isEmpty() ||
            duration == null || duration.trim().isEmpty() ||
            totalFeeStr == null || totalFeeStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/courses?error=Course+Name,+Duration,+and+Total+Fee+are+required.");
            return;
        }

        double totalFee;
        try {
            totalFee = Double.parseDouble(totalFeeStr);
            if (totalFee < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/courses?error=Invalid+fee+amount.+Must+be+a+positive+number.");
            return;
        }

        if (courseIdStr != null && !courseIdStr.trim().isEmpty()) {
            // Update
            int courseId = Integer.parseInt(courseIdStr);
            Course c = new Course(courseId, courseName.trim(), duration.trim(), totalFee, description);
            courseService.updateCourse(c);
            resp.sendRedirect(req.getContextPath() + "/admin/courses?msg=Course+updated+successfully.");
        } else {
            // Insert
            Course c = new Course(courseName.trim(), duration.trim(), totalFee, description);
            courseService.addCourse(c);
            resp.sendRedirect(req.getContextPath() + "/admin/courses?msg=Course+added+successfully.");
        }
    }
}
