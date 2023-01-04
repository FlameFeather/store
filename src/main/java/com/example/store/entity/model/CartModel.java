package com.example.store.entity.model;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CartModel extends EntityBase implements Serializable {
	private Integer cid;
	private Integer uid;
	private Integer pid;
	private Integer num;
	private Long price;
}