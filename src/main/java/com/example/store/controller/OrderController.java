package com.example.store.controller;

import com.example.store.response.ResponseJson;
import com.example.store.security.JwtHandle;
import com.example.store.service.IOrderService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
	private final IOrderService iOrderService;
	private final JwtHandle jwtHandle;

	@Autowired
	public OrderController(IOrderService iOrderService, JwtHandle jwtHandle) {
		this.iOrderService = iOrderService;
		this.jwtHandle = jwtHandle;
	}

	@RequestMapping("/create")
	@PreAuthorize("hasAnyAuthority('/order/add','/cart/update')")
	public ResponseJson<Void> create(Integer aid, @RequestHeader("Authentication") String jwt, Integer... cids) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iOrderService.create(aid, claims.get("id", Integer.class), cids));
	}
}
