package com.example.store.mapper;

import com.example.store.entity.vo.DetailsVO;
import com.example.store.entity.vo.ProductVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
	public List<ProductVO> get();
	public DetailsVO getDetailsById(Integer id);
}
