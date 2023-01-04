package com.example.store.entity.model;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserModel extends EntityBase implements Serializable {
	private Integer uid;
	private String username;
	private String password;
	private Integer gender;
	private String phone;
	private String email;
	private String avatar;
	private Integer isEnable;
}
