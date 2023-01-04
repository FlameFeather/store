package com.example.store.controller;

import com.example.store.entity.vo.DetailsVO;
import com.example.store.entity.vo.ProductVO;
import com.example.store.response.ResponseJson;
import com.example.store.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
	private final IProductService iProductService;

	@Autowired
	public ProductController(IProductService iProductService) {
		this.iProductService = iProductService;
	}

	@RequestMapping("/hot")
	public ResponseJson<List<ProductVO>> hot() {
		return ResponseJson.ok(iProductService.get());
	}

	@RequestMapping("/details")
	public ResponseJson<DetailsVO> details(Integer id) {
		return ResponseJson.ok(iProductService.details(id));
	}
}
