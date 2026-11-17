package com.tuitionfee.service;

import com.tuitionfee.dao.CourseDAO;
import com.tuitionfee.dao.StudentDAO;
import com.tuitionfee.dao.UserDAO;
import com.tuitionfee.model.Course;
import com.tuitionfee.model.Student;
import com.tuitionfee.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Service class handling student records and enrollment.
 * Managed by Spring IoC Container via Dependency Injection.
 */
public class StudentService {

    // Spring Dependency Injections configured in applicationContext.xml
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;

    public StudentService() {
    }

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public Student getStudentById(int studentId) {
        return studentDAO.findById(studentId);
    }

    public Student getStudentByUserId(int userId) {
        return studentDAO.findByUserId(userId);
    }

    public Student getStudentByRollNumber(String rollNumber) {
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            return null;
        }
        return studentDAO.findByRollNumber(rollNumber.trim());
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    /**
     * Generates a standardized, unique institutional roll number for a new student.
     * Roll numbers are strictly generated and assigned by the Administrator.
     */
    public String generateUniqueRollNumber() {
        int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        List<Student> all = studentDAO.findAll();
        int seq = all.size() + 1;
        String roll = String.format("STU-%d-%03d", year, seq);
        while (studentDAO.findByRollNumber(roll) != null) {
            seq++;
            roll = String.format("STU-%d-%03d", year, seq);
        }
        return roll;
    }

    /**
     * Creates a new Student profile along with an associated User login account.
     */
    public int createStudentWithAccount(String name, String email, String password, String phone,
                                        String rollNumber, int courseId, String address, Date admissionDate) {
        // 1. Create and save User account
        User user = new User(name, email, password, "STUDENT", phone);
        int userId = userDAO.save(user);
        if (userId <= 0) {
            return -1;
        }

        // 2. Fetch selected Course
        Course course = courseDAO.findById(courseId);

        // 3. Create Student profile
        Student student = new Student(user, rollNumber, course, address, admissionDate);
        return studentDAO.save(student);
    }

    public boolean updateStudent(int studentId, String name, String email, String phone,
                                 String rollNumber, int courseId, String address, Date admissionDate) {
        Student student = studentDAO.findById(studentId);
        if (student == null) {
            return false;
        }

        // Update linked user details
        User user = student.getUser();
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            userDAO.update(user);
        }

        // Update student fields
        student.setRollNumber(rollNumber);
        student.setAddress(address);
        student.setAdmissionDate(admissionDate);

        Course course = courseDAO.findById(courseId);
        student.setCourse(course);

        return studentDAO.update(student);
    }

    public boolean deleteStudent(int studentId) {
        Student student = studentDAO.findById(studentId);
        if (student != null) {
            int userId = student.getUser() != null ? student.getUser().getUserId() : 0;
            boolean deleted = studentDAO.delete(studentId);
            if (deleted && userId > 0) {
                userDAO.delete(userId);
            }
            return deleted;
        }
        return false;
    }
}
