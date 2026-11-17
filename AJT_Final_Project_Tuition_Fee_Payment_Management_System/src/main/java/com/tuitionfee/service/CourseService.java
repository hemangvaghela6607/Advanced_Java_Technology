package com.tuitionfee.service;

import com.tuitionfee.dao.CourseDAO;
import com.tuitionfee.model.Course;

import java.util.List;

/**
 * Service class handling course catalog and pricing.
 * Managed by Spring IoC Container via Setter Dependency Injection.
 */
public class CourseService {

    // Spring Dependency Injection: Injected via applicationContext.xml
    private CourseDAO courseDAO;

    public CourseService() {
    }

    /**
     * Setter method used by Spring for Dependency Injection.
     * @param courseDAO CourseDAO instance
     */
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Course getCourseById(int courseId) {
        return courseDAO.findById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public int addCourse(Course course) {
        return courseDAO.save(course);
    }

    public boolean updateCourse(Course course) {
        return courseDAO.update(course);
    }

    public boolean deleteCourse(int courseId) {
        return courseDAO.delete(courseId);
    }
}
