package com.example.store.service;

import com.example.store.entity.vo.CartVO;

import java.util.List;

public interface ICartService {
	public Boolean addToCart(Integer uid, Integer pid, Integer amount);

	public List<CartVO> getCart(Integer uid);

	public List<CartVO> getCartByCids(Integer uid, Integer[] cids);
}
