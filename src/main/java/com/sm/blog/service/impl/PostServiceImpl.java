package com.sm.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sm.blog.entities.Category;
import com.sm.blog.entities.Post;
import com.sm.blog.entities.User;
import com.sm.blog.exceptions.ResourceNotFoundException;
import com.sm.blog.payloads.ApiResponse;
import com.sm.blog.payloads.PostDto;
import com.sm.blog.payloads.PostResponse;
import com.sm.blog.repos.CategoryRepo;
import com.sm.blog.repos.PostRepo;
import com.sm.blog.repos.UserRepo;
import com.sm.blog.service.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

		Category cat = categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));

		Post post = modelMapper.map(postDto, Post.class);
		post.setPostImage("default.png");
		post.setAddedDate(new Date());

		post.setUser(user);
		post.setCategory(cat);

		Post addedPost = postRepo.save(post);
		return modelMapper.map(addedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImage(postDto.getPostImage());

		Post updatedPost = postRepo.save(post);

		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        postRepo.delete(post);
    }

	@Override
	public PostResponse getPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

//		Sort sort=null;
//		if(sortDir.equalsIgnoreCase("desc"))
//			sort=Sort.by(sortBy).descending();
//		else 
//			sort=Sort.by(sortBy).ascending();

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pagePost = postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	@Transactional
	public PostDto getPostById(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
//		 System.out.println("Fetched Post: " + post.getPostTitle());
//	        System.out.println("Number of Comments: " + post.getComments().size());
//	        post.getComments().forEach(comment -> System.out.println("Comment: " + comment.getContent()));
		 
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer catId) {

		Category cat = categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));
//		Page<Post> pagePost = postRepo.findByCategory(cat, pageable);
		List<Post> posts = postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

		List<Post> posts = postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepo.findByPostTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
