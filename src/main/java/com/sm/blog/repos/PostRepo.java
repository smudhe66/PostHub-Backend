package com.sm.blog.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sm.blog.entities.Category;
import com.sm.blog.entities.Post;
import com.sm.blog.entities.User;

import jakarta.transaction.Transactional;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByPostTitleContaining(String keyword);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
	
	}
