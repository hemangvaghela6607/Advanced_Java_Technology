package com.tuitionfee.dao;

import com.tuitionfee.model.Student;
import java.util.List;

/**
 * Data Access Object interface for Student operations using Hibernate.
 */
public interface StudentDAO {
    Student findById(int studentId);
    Student findByUserId(int userId);
    Student findByRollNumber(String rollNumber);
    List<Student> findAll();
    int save(Student student);
    boolean update(Student student);
    boolean delete(int studentId);
}
