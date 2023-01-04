package com.example.store.service.impl;

import com.example.store.entity.model.CartModel;
import com.example.store.entity.model.UserModel;
import com.example.store.entity.vo.CartVO;
import com.example.store.entity.vo.DetailsVO;
import com.example.store.mapper.CartMapper;
import com.example.store.mapper.ProductMapper;
import com.example.store.mapper.UserMapper;
import com.example.store.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements ICartService {
	private final CartMapper cartMapper;
	private final ProductMapper productMapper;
	private final UserMapper userMapper;

	@Autowired
	public CartServiceImpl(CartMapper cartModel, ProductMapper productMapper, UserMapper userMapper) {
		this.cartMapper = cartModel;
		this.productMapper = productMapper;
		this.userMapper = userMapper;
	}

	@Override
	public Boolean addToCart(Integer uid, Integer pid, Integer amount) {
		CartModel cart = cartMapper.getCartByUidPid(uid, pid);
		DetailsVO detailsVO = productMapper.getDetailsById(pid);
		UserModel userModel = userMapper.getById(uid);
		if (cart != null) {
			cartMapper.updateAmount(uid, pid, cart.getNum() + amount);
			cartMapper.updatePrice(uid, pid, detailsVO.getPrice());
		} else {
			CartModel cartModel = new CartModel();
			cartModel.setUid(uid);
			cartModel.setPid(pid);
			cartModel.setNum(amount);
			cartModel.setPrice(detailsVO.getPrice());
			cartModel.setCreatedUser(userModel.getUsername());
			cartModel.setCreatedTime(LocalDateTime.now());
			cartModel.setModifiedTime(LocalDateTime.now());
			cartModel.setModifiedUser(userModel.getUsername());
			cartMapper.addToCart(cartModel);
		}
		return true;
	}

	@Override
	public List<CartVO> getCart(Integer uid) {
		return cartMapper.getCartByUid(uid);
	}

	@Override
	public List<CartVO> getCartByCids(Integer uid, Integer[] cids) {
		log.info("{}{}", uid, cids);
		return cartMapper.getCartByCids(uid, cids);
	}
}
