package com.sm.blog.service;

import java.util.List;

import com.sm.blog.entities.Post;
import com.sm.blog.payloads.ApiResponse;
import com.sm.blog.payloads.PostDto;
import com.sm.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId, Integer catId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getPosts(Integer pageNo, Integer pageSize, String sortBy,String sortDir);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostsByCategory(Integer catId);
	
	List<PostDto> getPostsByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);

	
	
	
}
