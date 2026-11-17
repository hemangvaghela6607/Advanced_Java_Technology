package com.tuitionfee.dao;

import com.tuitionfee.model.User;
import com.tuitionfee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC-based implementation of UserDAO.
 * Demonstrates the use of PreparedStatement to protect against SQL Injection,
 * connection lifecycle management, and clean resource closing with try-with-resources.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT user_id, name, email, password, role, phone, created_at FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.findByEmailAndPassword] Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User findById(int userId) {
        String sql = "SELECT user_id, name, email, password, role, phone, created_at FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.findById] Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT user_id, name, email, password, role, phone, created_at FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.findByEmail] Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (name, email, password, role, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPhone());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        user.setUserId(generatedId);
                        return generatedId;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.save] Error: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, phone = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.update] Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.delete] Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, name, email, password, role, phone, created_at FROM users ORDER BY user_id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("[UserDAOImpl.findAll] Error: " + e.getMessage());
        }
        return list;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        u.setPhone(rs.getString("phone"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        return u;
    }
}
