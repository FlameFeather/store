package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartVO implements Serializable {
	private Integer cid;
	private String image;
	private String title;
	private Long realPrice;
	private Integer num;
}
