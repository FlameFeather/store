package com.example.store.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/user/avatar/**")
				.addResourceLocations("file:D:\\JavaWebProgram\\store\\src\\main\\resources\\static\\image\\user\\avatar\\");
	}
}
