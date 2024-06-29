package com.sm.blog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;



@Service
public class UserRoleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteAllUserRolesForUser(Integer userId) {
        // Create a native SQL query to delete user_role entries for the given user id
        String sql = "DELETE FROM user_role WHERE user_id = :userId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
    
    public boolean hasUserRoles(Integer userId) {
        // Check if user roles exist for the given user id
        String sql = "SELECT COUNT(*) FROM user_role WHERE user_id = :userId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        Number count = (Number) query.getSingleResult(); // Use Number to handle any numeric type
        return count.intValue() > 0;
    }
}
