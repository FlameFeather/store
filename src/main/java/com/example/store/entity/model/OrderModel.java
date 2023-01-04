package com.example.store.entity.model;

import com.example.store.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OrderModel extends EntityBase implements Serializable {
	private Integer oid;
	private Integer uid;
	private String recvName;
	private String recvPhone;
	private String recvProvince;
	private String recvCity;
	private String recvArea;
	private String recvAddress;
	private Long price;
	private LocalDateTime orderTime;
	private LocalDateTime payTime;
	private Integer status;
}
