package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressVO implements Serializable {
	private Integer aid;
	private String tag;
	private String receiver;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String address;
	private String phone;
	private Integer isDefault;
}
