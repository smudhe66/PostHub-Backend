package com.sm.blog;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.sm.blog.config.AppConstant;
import com.sm.blog.entities.Role;
import com.sm.blog.repos.RoleRepo;



@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
	
		try {
			
			if(roleRepo.count()==0)
			{
				Role role1 = new Role();
				role1.setId(AppConstant.ROLE_USER);
				role1.setName("ROLE_USER");
				
				Role role2 = new Role();
				role2.setId(AppConstant.ROLE_ADMIN);
				role2.setName("ROLE_ADMIN");
				
				List<Role> roles = List.of(role1,role2);
				
				List<Role> saveAll = roleRepo.saveAll(roles);
				
				saveAll.forEach(r->System.out.println(r.getName())) ;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
