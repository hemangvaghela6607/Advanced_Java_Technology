package com.tuitionfee.dao;

import com.tuitionfee.model.Course;
import java.util.List;

/**
 * Data Access Object interface for Course operations.
 */
public interface CourseDAO {
    Course findById(int courseId);
    List<Course> findAll();
    int save(Course course);
    boolean update(Course course);
    boolean delete(int courseId);
}
