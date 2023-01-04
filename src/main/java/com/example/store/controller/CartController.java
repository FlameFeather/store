package com.example.store.controller;

import com.example.store.entity.vo.CartVO;
import com.example.store.response.ResponseJson;
import com.example.store.security.JwtHandle;
import com.example.store.service.ICartService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
	private final ICartService iCartService;
	private final JwtHandle jwtHandle;

	@Autowired
	public CartController(ICartService iCartService, JwtHandle jwtHandle) {
		this.iCartService = iCartService;
		this.jwtHandle = jwtHandle;
	}

	@RequestMapping("/add_to_cart")
	@PreAuthorize("hasAnyAuthority('/cart/add')")
	public ResponseJson<Boolean> addToCart(Integer pid, Integer amount, @RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		log.info("{}加入购物车处理", pid);
		return ResponseJson.ok(iCartService.addToCart(claims.get("id", Integer.class), pid, amount));
	}
	@RequestMapping("/")
	@PreAuthorize("hasAnyAuthority('/cart/show')")
	public ResponseJson<List<CartVO>> show(Integer pid, Integer amount, @RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iCartService.getCart(claims.get("id", Integer.class)));
	}
	@RequestMapping("/get_by_cids")
	@PreAuthorize("hasAnyAuthority('/cart/show')")
	public ResponseJson<List<CartVO>> getByCids(@RequestHeader("Authentication") String jwt, Integer... cids) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iCartService.getCartByCids(claims.get("id", Integer.class), cids));
	}
}
