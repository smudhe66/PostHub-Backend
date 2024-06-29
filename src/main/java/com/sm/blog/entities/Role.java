package com.sm.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	public Role(String name) {
        this.name = name;
    }
	
	public Role() {}
	
	 @Override
	    public String toString() {
	        return name; // Adjust to return just the name
	    }
}
