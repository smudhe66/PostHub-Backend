package com.sm.blog;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.blog.repos.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest()
	{
		String name = userRepo.getClass().getName();
		String packageName = userRepo.getClass().getPackageName();
		System.out.println(name);
		System.out.println(packageName);
	}

}
