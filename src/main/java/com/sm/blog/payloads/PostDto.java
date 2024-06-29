package com.sm.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class PostDto {

	private Integer postId;

	private String postTitle;

	private String postContent;

	private String postImage;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	private List<CommentDto> comments = new ArrayList<>();
}
