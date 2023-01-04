package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetailsVO implements Serializable {
	private String title;
	private String sellPoint;
	private Long price;
	private String image;
}
