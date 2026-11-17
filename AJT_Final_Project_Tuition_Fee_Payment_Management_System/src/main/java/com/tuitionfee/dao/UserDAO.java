package com.tuitionfee.dao;

import com.tuitionfee.model.User;
import java.util.List;

/**
 * Data Access Object interface for User entity.
 */
public interface UserDAO {
    User findByEmailAndPassword(String email, String password);
    User findById(int userId);
    User findByEmail(String email);
    int save(User user);
    boolean update(User user);
    boolean delete(int userId);
    List<User> findAll();
}
