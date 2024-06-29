package com.sm.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;

import com.sm.blog.entities.Comment;
import com.sm.blog.entities.Post;
import com.sm.blog.entities.User;
import com.sm.blog.exceptions.ResourceNotFoundException;
import com.sm.blog.payloads.CommentDto;
import com.sm.blog.repos.CommentRepo;
import com.sm.blog.repos.PostRepo;
import com.sm.blog.repos.UserRepo;
import com.sm.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = commentRepo.save(comment);
		
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
		
		commentRepo.delete(comment);

	}

}
