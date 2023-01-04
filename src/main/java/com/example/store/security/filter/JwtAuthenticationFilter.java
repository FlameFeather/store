package com.example.store.security.filter;

import com.alibaba.fastjson2.JSON;
import com.example.store.response.ResponseJson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String jwt = request.getHeader("Authentication");
		if (StringUtils.hasText(jwt)) {
			if (!jwt.startsWith("Bearer ")) {
				log.error("jwt:{}数据错误", jwt);
				ResponseJson<Void> jsonResult = ResponseJson.fail(ResponseJson.State.ERR_FORBIDDEN, "jwt数据错误");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().println(JSON.toJSONString(jsonResult));
//				filterChain.doFilter(request, response);
				return;
			}
			log.debug("准备解析jwt数据");
			String JWT = jwt.substring(7);
			try {
				JwtParser parser = Jwts.parser();
				Claims claims = parser.setSigningKey(secretKey).parseClaimsJws(JWT).getBody();
				String username = claims.get("username").toString();
				String permissions = claims.get("permissions").toString();
				Long id = Long.parseLong(claims.get("id").toString());
				log.debug("从JWT中获得username:{}", username);
				log.debug("permissions:{}", permissions);
				List<SimpleGrantedAuthority> authorities = JSON.parseArray(permissions, SimpleGrantedAuthority.class);
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (ExpiredJwtException e) {
				log.error("jwt过期");
				ResponseJson<Void> jsonResult = ResponseJson.fail(ResponseJson.State.ERR_FORBIDDEN, "登入超时，请重新登录");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().println(JSON.toJSONString(jsonResult));
				return;
			} catch (Exception e) {
				log.error("jwt数据错误");
				ResponseJson<Void> jsonResult = ResponseJson.fail(ResponseJson.State.ERR_FORBIDDEN, "jwt数据错误");
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().println(JSON.toJSONString(jsonResult));
				return;
			}
			filterChain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
