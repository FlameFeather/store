package com.example.store.service;

import com.example.store.entity.vo.DetailsVO;
import com.example.store.entity.vo.ProductVO;

import java.util.List;

public interface IProductService {
	public List<ProductVO> get();
	public DetailsVO details(Integer id);
}
