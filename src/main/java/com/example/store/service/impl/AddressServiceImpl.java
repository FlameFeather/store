package com.example.store.service.impl;

import com.example.store.entity.dto.NewAddressDTO;
import com.example.store.entity.model.AddressModel;
import com.example.store.entity.vo.AddressVO;
import com.example.store.entity.vo.DistrictVO;
import com.example.store.exception.ServiceException;
import com.example.store.mapper.AddressMapper;
import com.example.store.response.ResponseJson;
import com.example.store.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
	private final AddressMapper addressMapper;

	@Autowired
	public AddressServiceImpl(AddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}

	@Override
	public List<DistrictVO> getProvinces(String parent) {
		return addressMapper.getDistrict(parent);
	}

	@Override
	public Void addAddress(NewAddressDTO newAddressDTO, Integer uid, String username) {
		AddressModel addressModel = new AddressModel();
		addressModel.setUid(uid);
		addressModel.setReceiver(newAddressDTO.getReceiver());
		addressModel.setProvinceCode(newAddressDTO.getProvinceCode());
		addressModel.setProvinceName(addressMapper.getDistrictNameByCode(newAddressDTO.getProvinceCode()));
		addressModel.setCityCode(newAddressDTO.getCityCode());
		addressModel.setCityName(addressMapper.getDistrictNameByCode(newAddressDTO.getCityCode()));
		addressModel.setAreaCode(newAddressDTO.getAreaCode());
		addressModel.setAreaName(addressMapper.getDistrictNameByCode(newAddressDTO.getAreaCode()));
		addressModel.setZip(newAddressDTO.getZip());
		addressModel.setAddress(newAddressDTO.getAddress());
		addressModel.setPhone(newAddressDTO.getPhone());
		addressModel.setTel(newAddressDTO.getTel());
		addressModel.setTag(newAddressDTO.getTag());
		addressModel.setIsDefault(0);
		addressModel.setCityName(username);
		addressModel.setCreatedUser(username);
		addressModel.setCreatedTime(LocalDateTime.now());
		addressModel.setModifiedTime(LocalDateTime.now());
		addressModel.setModifiedUser(username);
		if (addressMapper.addAddress(addressModel) != 1)
			throw new ServiceException(ResponseJson.State.ERR_INTERNAL_ERROR, "新增地址失败请稍后重试");
		return null;
	}

	@Override
	public List<AddressVO> getAddresses(Integer uid) {
		return addressMapper.getAddresses(uid);
	}

	@Override
	public Void setDefault(Integer uid, Integer aid) {
		if (addressMapper.clearDefault(uid) == 0)
			throw new ServiceException(ResponseJson.State.ERR_INTERNAL_ERROR, "系统故障1");
		if (addressMapper.setDefault(uid, aid) == 0)
			throw new ServiceException(ResponseJson.State.ERR_INTERNAL_ERROR, "系统故障2");
		return null;
	}

	@Override
	public Void delete(Integer uid, Integer aid) {
		if (addressMapper.delete(uid, aid) == 0)
			throw new ServiceException(ResponseJson.State.ERR_INTERNAL_ERROR, "删除失败");
		return null;
	}
}
