package com.example.store.security.config;

import com.example.store.security.filter.JwtAuthenticationFilter;
import com.example.store.security.handle.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final MyAccessDeniedHandler myAccessDeniedHandler;

	@Autowired
	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, MyAccessDeniedHandler myAccessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.myAccessDeniedHandler = myAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String[] urls = {
				"/bootstrap3/**",
				"/images/**",
				"/**/*.js",
				"/**/*.ico",
				"/user/checking",
				"/**/*.html",
				"/image/**",
				"/product/**",
				"/css/**",
				"/user/login",
				"/user/register"};
		http.authorizeRequests()
				.antMatchers(urls)
				.permitAll()  //允许/web路径下的url，这里可以自己根据需求定制
				.anyRequest().authenticated()    //操作必须是已登录状态
				.and()
				.formLogin()
//				.loginProcessingUrl("/users/login")
				.loginPage("/web/login.html")
//				.successForwardUrl("/user/toIndex")
				.permitAll()     //自定义登录页面权限放开
				.and()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
				.and()
				.csrf().disable();
		return http.build();
	}
}
