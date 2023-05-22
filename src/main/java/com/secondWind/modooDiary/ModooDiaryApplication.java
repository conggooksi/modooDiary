package com.secondWind.modooDiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ModooDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModooDiaryApplication.class, args);
	}
}
