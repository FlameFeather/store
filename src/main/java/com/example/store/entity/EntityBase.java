package com.example.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EntityBase implements Serializable {
	private String createdUser;
	private LocalDateTime createdTime;
	private String modifiedUser;
	private LocalDateTime modifiedTime;
}
