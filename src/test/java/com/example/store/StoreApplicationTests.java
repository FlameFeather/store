package com.example.store;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
class StoreApplicationTests {

	@Test
	void contextLoads() {
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		log.info(passwordEncoder.encode("123"));
	}

}
