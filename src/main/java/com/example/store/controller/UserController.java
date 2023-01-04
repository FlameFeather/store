package com.example.store.controller;

import com.example.store.entity.dto.InfoChangeDTO;
import com.example.store.entity.dto.PasswordChangeDTO;
import com.example.store.entity.dto.UserLoginDTO;
import com.example.store.entity.vo.AvatarChangeVO;
import com.example.store.entity.vo.UserDetailsVO;
import com.example.store.file.FileHandle;
import com.example.store.response.ResponseJson;
import com.example.store.security.JwtHandle;
import com.example.store.service.IUserService;
import com.example.store.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	private final IUserService userService;
	private final JwtHandle jwtHandle;

	@Autowired
	public UserController(IUserService userService, JwtHandle jwtHandle, FileHandle fileHandle) {
		this.userService = userService;
		this.jwtHandle = jwtHandle;
	}

	@RequestMapping("/login")
	public ResponseJson<String> login(UserLoginDTO UserLoginDTO) {
		return ResponseJson.ok(userService.login(UserLoginDTO));
	}

	@RequestMapping("/register")
	public ResponseJson<String> register(UserLoginDTO UserLoginDTO) {
		return ResponseJson.ok(userService.register(UserLoginDTO));
	}

	@RequestMapping("/checking")
	public ResponseJson<String> checking(String jwt) {
		return ResponseJson.ok("ok");
	}

	@RequestMapping("password/change")
	@PreAuthorize("hasAnyAuthority('/user/update')")
	public ResponseJson<Boolean> passwordChange(@RequestHeader("Authentication") String jwt, PasswordChangeDTO passwordChangeDTO) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		passwordChangeDTO.setUsername(claims.get("username", String.class));
		return ResponseJson.ok(userService.passwordChange(passwordChangeDTO));
	}

	@RequestMapping("/info/show")
	@PreAuthorize("hasAnyAuthority('/user/show')")
	public ResponseJson<UserDetailsVO> infoShow(@RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(userService.infoShow(claims.get("username", String.class)));
	}

	@RequestMapping("/info/change")
	@PreAuthorize("hasAnyAuthority('/user/update')")
	public ResponseJson<Integer> infoChange(InfoChangeDTO infoChangeDTO, @RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		infoChangeDTO.setUsername(claims.get("username", String.class));
		return ResponseJson.ok(userService.infoChange(infoChangeDTO));
	}

	@RequestMapping("/avatar/change")
	@PreAuthorize("hasAnyAuthority('/user/update')")
	public ResponseJson<AvatarChangeVO> avatarChange(MultipartFile file, @RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		String username = claims.get("username", String.class);
		long maxSize = 1024 * 1024 * 10;//10M
		return ResponseJson.ok(userService.avatarChange(username,maxSize,file));
	}

}
