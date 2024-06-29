package com.sm.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.blog.payloads.ApiResponse;
import com.sm.blog.payloads.CategoryDto;
import com.sm.blog.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
@Tag(name = "Category Controller",description = "APIs for Category.")
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto catDto = catService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId)
	{
		CategoryDto catDto = catService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		catService.deleteCategory(catId);
		return new ResponseEntity(new ApiResponse("Category is deleted successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCatgoryById(@PathVariable Integer catId)
	{
		CategoryDto catDto = catService.getCategoryById(catId);
		return ResponseEntity.ok(catDto);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCatgories()
	{
		System.out.println("In cat controlller");
		List<CategoryDto> catsDto = catService.getCategories();
		return ResponseEntity.ok(catsDto);
	}
}
