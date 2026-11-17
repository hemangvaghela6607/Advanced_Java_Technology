package com.tuitionfee.service;

import com.tuitionfee.dao.UserDAO;
import com.tuitionfee.model.User;

import java.util.List;

/**
 * Service class handling user authentication and profile management.
 * Managed by Spring IoC Container via Setter Dependency Injection.
 */
public class UserService {

    // Spring Dependency Injection: Injected via applicationContext.xml
    private UserDAO userDAO;

    public UserService() {
    }

    /**
     * Setter method used by Spring for Dependency Injection.
     * @param userDAO UserDAO instance
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticate(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userDAO.findByEmailAndPassword(email.trim(), password.trim());
    }

    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email != null ? email.trim() : null);
    }

    public int registerUser(User user) {
        if (user == null || user.getEmail() == null) {
            return -1;
        }
        // Check if email already registered
        User existing = userDAO.findByEmail(user.getEmail().trim());
        if (existing != null) {
            return -2; // Code -2 indicates email already exists
        }
        return userDAO.save(user);
    }

    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.delete(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
