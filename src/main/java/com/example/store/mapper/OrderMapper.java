package com.example.store.mapper;

import com.example.store.entity.model.OrderItemModel;
import com.example.store.entity.model.OrderModel;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
	public Integer add(OrderModel orderModel);
	public Integer addItem(OrderItemModel orderItemModel);
}
