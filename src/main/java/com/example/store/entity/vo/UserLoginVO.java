package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLoginVO implements Serializable {
	private Integer uid;
	private String username;
	private String password;
	private Integer isEnable;
	private List<PermissionVO> permissions;
}
