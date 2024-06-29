package com.sm.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
