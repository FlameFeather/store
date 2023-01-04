package com.example.store.entity.model;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AddressModel extends EntityBase implements Serializable {
	private Integer aid;
	private Integer uid;
	private String receiver;
	private String provinceName;
	private String provinceCode;
	private String cityName;
	private String cityCode;
	private String areaName;
	private String areaCode;
	private String zip;
	private String address;
	private String phone;
	private String tel;
	private String tag;
	private Integer isDefault;
}
