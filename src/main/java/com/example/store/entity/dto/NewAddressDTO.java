package com.example.store.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewAddressDTO implements Serializable {
	private String receiver;
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	private String zip;
	private String address;
	private String phone;
	private String tel;
	private String tag;
}
