package com.sm.blog.payloads;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {

	private List<PostDto> content;
	private Integer pageNo;
	private Integer pageSize;
	private long totalElement;
	private Integer totalPages;
	
	private boolean isLastPage;
}
