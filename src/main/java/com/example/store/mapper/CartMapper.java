package com.example.store.mapper;

import com.example.store.entity.model.CartModel;
import com.example.store.entity.vo.CartVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
	public Integer addToCart(CartModel cartModel);

	public CartModel getCartByUidPid(Integer uid, Integer pid);

	public Integer updateAmount(Integer uid, Integer pid, Integer amount);

	public Integer updatePrice(Integer uid, Integer pid, Long price);

	public List<CartVO> getCartByUid(Integer uid);

	public List<CartVO> getCartByCids(Integer uid, Integer[] cids);

	public CartModel getCartByCid(Integer cid);

	public Integer deleteByIds(Integer... cids);
}
