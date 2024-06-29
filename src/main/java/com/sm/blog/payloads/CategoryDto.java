package com.sm.blog.payloads;

import jakarta.persistence.Column;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=4)
	private String categoryTitle;
	
	@Size(min=10)
	@NotEmpty
	private String categoryDescription;
}
