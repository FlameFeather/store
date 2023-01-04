package com.example.store.security;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHandle {
	@Value("${jwt.secret}")
	private String secretKey;
	@Value("${jwt.expiration}")
	private Long expiration;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public JwtHandle(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String getJWT(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", userDetails.getUid());
		claims.put("username", userDetails.getUsername());
		claims.put("permissions", JSON.toJSONString(userDetails.getAuthorities()));
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam(Header.CONTENT_TYPE, "HS256")
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public String encoding(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public Boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public Claims getClaims(String JWT) {
		JwtParser parser = Jwts.parser();
		return parser.setSigningKey(secretKey).parseClaimsJws(JWT).getBody();
	}
}
