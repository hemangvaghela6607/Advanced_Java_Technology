package com.tuitionfee.dao;

import com.tuitionfee.model.FeeAssignment;
import java.util.List;

/**
 * Data Access Object interface for FeeAssignment using Hibernate.
 */
public interface FeeAssignmentDAO {
    FeeAssignment findById(int assignmentId);
    List<FeeAssignment> findByStudentId(int studentId);
    List<FeeAssignment> findAll();
    int save(FeeAssignment feeAssignment);
    boolean update(FeeAssignment feeAssignment);
    boolean delete(int assignmentId);
}
