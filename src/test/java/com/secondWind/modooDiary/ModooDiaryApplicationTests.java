package com.secondWind.modooDiary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ModooDiaryApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void makeSecretKey() {
		String secretKey = UUID.randomUUID().toString();
		System.out.println("secretKey = " + secretKey);
	}

}
