package com.sm.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
