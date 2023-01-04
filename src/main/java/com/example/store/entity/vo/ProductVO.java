package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductVO implements Serializable {
	private Integer id;
	private String title;
	private Long price;
	private String image;
}
