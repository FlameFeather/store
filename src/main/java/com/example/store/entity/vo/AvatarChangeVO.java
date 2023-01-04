package com.example.store.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AvatarChangeVO implements Serializable {
	public AvatarChangeVO(String path, String username) {
		this.path = path;
		this.username = username;
	}

	private String path;
	private String username;

}
