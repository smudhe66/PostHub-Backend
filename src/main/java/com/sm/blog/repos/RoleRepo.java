package com.sm.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

