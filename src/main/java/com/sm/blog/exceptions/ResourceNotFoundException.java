package com.sm.blog.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String fieldName;
	long fieldValue;
	String filedValueString;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String filedValueString) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,filedValueString));
		
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.filedValueString = filedValueString;
	}
	
	
	
}
