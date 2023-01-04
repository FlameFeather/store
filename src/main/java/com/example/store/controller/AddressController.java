package com.example.store.controller;

import com.example.store.entity.dto.NewAddressDTO;
import com.example.store.entity.vo.AddressVO;
import com.example.store.entity.vo.DistrictVO;
import com.example.store.response.ResponseJson;
import com.example.store.security.JwtHandle;
import com.example.store.service.IAddressService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {
	private final JwtHandle jwtHandle;
	private final IAddressService iAddressService;

	@Autowired
	public AddressController(IAddressService iAddressService, JwtHandle jwtHandle) {
		this.iAddressService = iAddressService;
		this.jwtHandle = jwtHandle;
	}

	@RequestMapping("/districts/")
	@PreAuthorize("hasAnyAuthority('/address/add')")
	public ResponseJson<List<DistrictVO>> getDistricts(String parent) {
		return ResponseJson.ok(iAddressService.getProvinces(parent));
	}

	@RequestMapping("/addNew")
	@PreAuthorize("hasAnyAuthority('/address/add')")
	public ResponseJson<Void> getDistricts(NewAddressDTO newAddressDTO, @RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iAddressService.addAddress(newAddressDTO
				, claims.get("id", Integer.class), claims.get("username", String.class)));
	}

	@RequestMapping("/getAddresses")
	@PreAuthorize("hasAnyAuthority('/address/show')")
	public ResponseJson<List<AddressVO>> getAddresses(@RequestHeader("Authentication") String jwt) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iAddressService.getAddresses(claims.get("id", Integer.class)));
	}

	@RequestMapping("/set_default")
	@PreAuthorize("hasAnyAuthority('/address/show')")
	public ResponseJson<Void> setDefault(@RequestHeader("Authentication") String jwt, Integer aid) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iAddressService.setDefault(claims.get("id", Integer.class), aid));
	}

	@RequestMapping("/delete")
	@PreAuthorize("hasAnyAuthority('/address/delete')")
	public ResponseJson<Void> delete(@RequestHeader("Authentication") String jwt, Integer aid) {
		Claims claims = jwtHandle.getClaims(jwt.substring(7));
		return ResponseJson.ok(iAddressService.delete(claims.get("id", Integer.class), aid));
	}
}
