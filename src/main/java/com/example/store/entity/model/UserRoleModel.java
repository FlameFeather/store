package com.example.store.entity.model;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserRoleModel extends EntityBase implements Serializable {
	private Integer id;
	private Integer uid;
	private Integer rid;
}
