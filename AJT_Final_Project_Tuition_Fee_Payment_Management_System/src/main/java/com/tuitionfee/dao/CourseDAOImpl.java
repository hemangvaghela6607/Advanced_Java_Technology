package com.tuitionfee.dao;

import com.tuitionfee.model.Course;
import com.tuitionfee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC-based implementation of CourseDAO.
 * Demonstrates CRUD operations using standard JDBC PreparedStatement.
 */
public class CourseDAOImpl implements CourseDAO {

    @Override
    public Course findById(int courseId) {
        String sql = "SELECT course_id, course_name, duration, total_fee, description FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractCourse(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAOImpl.findById] Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_id, course_name, duration, total_fee, description FROM courses ORDER BY course_id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractCourse(rs));
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAOImpl.findAll] Error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int save(Course course) {
        String sql = "INSERT INTO courses (course_name, duration, total_fee, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDuration());
            ps.setDouble(3, course.getTotalFee());
            ps.setString(4, course.getDescription());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        course.setCourseId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAOImpl.save] Error: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean update(Course course) {
        String sql = "UPDATE courses SET course_name = ?, duration = ?, total_fee = ?, description = ? WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDuration());
            ps.setDouble(3, course.getTotalFee());
            ps.setString(4, course.getDescription());
            ps.setInt(5, course.getCourseId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[CourseDAOImpl.update] Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[CourseDAOImpl.delete] Error: " + e.getMessage());
        }
        return false;
    }

    private Course extractCourse(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setCourseName(rs.getString("course_name"));
        c.setDuration(rs.getString("duration"));
        c.setTotalFee(rs.getDouble("total_fee"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
