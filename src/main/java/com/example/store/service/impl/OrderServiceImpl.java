package com.example.store.service.impl;

import com.example.store.entity.model.*;
import com.example.store.exception.ServiceException;
import com.example.store.mapper.*;
import com.example.store.response.ResponseJson;
import com.example.store.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
	private final OrderMapper orderMapper;
	private final AddressMapper addressMapper;
	private final CartMapper cartMapper;
	private final UserMapper userMapper;
	private final ProductMapper productMapper;

	@Autowired
	public OrderServiceImpl(ProductMapper productMapper, OrderMapper orderMapper, AddressMapper addressMapper, CartMapper cartMapper, UserMapper userMapper) {
		this.orderMapper = orderMapper;
		this.addressMapper = addressMapper;
		this.cartMapper = cartMapper;
		this.userMapper = userMapper;
		this.productMapper = productMapper;
	}

	@Override
	public Void create(Integer aid, Integer uid, Integer... cids) {
		AddressModel addressModel = addressMapper.getAddressById(aid);
		if (addressModel == null) throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "无收货地址信息");
		long price = 0L;
		for (Integer cid : cids) {
			CartModel cartModel = cartMapper.getCartByCid(cid);
			price += cartModel.getPrice() * cartModel.getNum();
		}
		OrderModel orderModel = new OrderModel();
		orderModel.setUid(uid);
		orderModel.setRecvName(addressModel.getReceiver());
		orderModel.setRecvPhone(addressModel.getPhone());
		orderModel.setRecvProvince(addressModel.getProvinceCode());
		orderModel.setRecvCity(addressModel.getCityCode());
		orderModel.setRecvArea(addressModel.getAreaCode());
		orderModel.setRecvAddress(addressModel.getAddress());
		orderModel.setPrice(price);
		orderModel.setOrderTime(LocalDateTime.now());
		orderModel.setPayTime(null);
		orderModel.setStatus(0);
		UserModel userModel = userMapper.getById(uid);
		orderModel.setCreatedUser(userModel.getUsername());
		orderModel.setCreatedTime(LocalDateTime.now());
		orderModel.setModifiedUser(userModel.getUsername());
		orderModel.setModifiedTime(LocalDateTime.now());
		orderMapper.add(orderModel);


		for (Integer cid : cids) {
			OrderItemModel orderItemModel = new OrderItemModel();
			orderItemModel.setCreatedTime(LocalDateTime.now());
			orderItemModel.setCreatedUser(userModel.getUsername());
			orderItemModel.setModifiedTime(LocalDateTime.now());
			orderItemModel.setModifiedUser(userModel.getUsername());
			orderItemModel.setOid(orderModel.getOid());
			orderItemModel.setPid(cartMapper.getCartByCid(cid).getPid());
			orderItemModel.setTitle(productMapper.getDetailsById(cartMapper.getCartByCid(cid).getPid()).getTitle());
			orderItemModel.setImage(productMapper.getDetailsById(cartMapper.getCartByCid(cid).getPid()).getImage());
			orderItemModel.setPrice(cartMapper.getCartByCid(cid).getPrice());
			orderItemModel.setNum(cartMapper.getCartByCid(cid).getNum());
			orderMapper.addItem(orderItemModel);
		}
		cartMapper.deleteByIds(cids);
		return null;
	}
}
