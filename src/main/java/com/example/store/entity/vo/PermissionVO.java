package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionVO implements Serializable {
	private Integer id;
	private String name;
	private String value;
}
