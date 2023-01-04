package com.example.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCommandRunner implements CommandLineRunner {
	@Override
	public void run(String... args) {
		String mainPage = "http://localhost:8080/";
		log.info("开始自动加载页面:{}", mainPage);
//hot-fix
		//master-test
//		try {
//			Runtime.getRuntime().exec("cmd   /c   start   " + mainPage);//可以指定自己的路径
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

}