package com.tuitionfee.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity representing a tuition course / batch.
 */
@Entity
@Table(name = "courses")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name", nullable = false, length = 120)
    private String courseName;

    @Column(name = "duration", nullable = false, length = 50)
    private String duration;

    @Column(name = "total_fee", nullable = false)
    private double totalFee;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Course() {
    }

    public Course(String courseName, String duration, double totalFee, String description) {
        this.courseName = courseName;
        this.duration = duration;
        this.totalFee = totalFee;
        this.description = description;
    }

    public Course(int courseId, String courseName, String duration, double totalFee, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.totalFee = totalFee;
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
