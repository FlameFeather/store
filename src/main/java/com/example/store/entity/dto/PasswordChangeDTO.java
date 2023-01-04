package com.example.store.entity.dto;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PasswordChangeDTO extends EntityBase implements Serializable {
	private String username;
	private String oldPassword;
	private String newPassword;
}
