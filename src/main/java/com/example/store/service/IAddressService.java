package com.example.store.service;

import com.example.store.entity.dto.NewAddressDTO;
import com.example.store.entity.vo.AddressVO;
import com.example.store.entity.vo.DistrictVO;

import java.util.List;

public interface IAddressService {
	public List<DistrictVO> getProvinces(String parent);

	public Void addAddress(NewAddressDTO newAddressDTO, Integer uid, String username);

	public List<AddressVO> getAddresses(Integer uid);
	public Void setDefault(Integer uid,Integer aid);

	public Void delete(Integer uid,Integer aid);
}
