package com.example.store.service.impl;

import com.example.store.entity.vo.DetailsVO;
import com.example.store.entity.vo.ProductVO;
import com.example.store.mapper.ProductMapper;
import com.example.store.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
	private final ProductMapper productMapper;

	@Autowired
	public ProductServiceImpl(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public List<ProductVO> get() {
		return productMapper.get();
	}

	@Override
	public DetailsVO details(Integer id) {
		return productMapper.getDetailsById(id);
	}
}
