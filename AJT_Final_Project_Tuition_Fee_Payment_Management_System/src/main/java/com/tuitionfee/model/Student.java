package com.tuitionfee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Entity representing a student profile, mapped to a User account and enrolled Course.
 */
@Entity
@Table(name = "students")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "roll_number", nullable = false, unique = true, length = 50)
    private String rollNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "admission_date", nullable = false)
    private Date admissionDate;

    public Student() {
    }

    public Student(User user, String rollNumber, Course course, String address, Date admissionDate) {
        this.user = user;
        this.rollNumber = rollNumber;
        this.course = course;
        this.address = address;
        this.admissionDate = admissionDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
}
