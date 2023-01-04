package com.example.store.service;

public interface IOrderService {
	public Void create(Integer aid, Integer uid, Integer... cids);
}
