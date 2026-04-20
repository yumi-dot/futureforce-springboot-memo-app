package com.lesson.memo;

//import jakarta.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoApplication.class, args);
	}
	
//	@PostConstruct
//	public void test() {
//	    System.out.println("APP START OK");
//	}

}
