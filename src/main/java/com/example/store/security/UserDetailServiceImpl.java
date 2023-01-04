package com.example.store.security;

import com.example.store.entity.vo.PermissionVO;
import com.example.store.entity.vo.UserLoginVO;
import com.example.store.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	@Autowired
	public UserDetailServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper) {
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("开始认证用户：{}", username);
		UserLoginVO userLoginVO = userMapper.getLoginInfoByUsername(username);
		if (userLoginVO == null) {
			log.info("用户：{}认证失败", username);
			throw new BadCredentialsException(username + " 用户不存在！");
		}
		log.info("用户：{}存在", username);
		com.example.store.security.UserDetails userDetails = new com.example.store.security.UserDetails();
		userDetails.setUid(userLoginVO.getUid());
		userDetails.setUsername(userLoginVO.getUsername());
		userDetails.setPassword(userLoginVO.getPassword());

		List<PermissionVO> permissions = userLoginVO.getPermissions();
		log.info("用户:{}权限信息：{}", username, permissions);
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (PermissionVO permission : permissions) {
			String permissionValue = permission.getValue();
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
			authorities.add(authority);
		}
		userDetails.setAuthorities(authorities);
		return userDetails;
	}
}
