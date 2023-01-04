package com.example.store.mapper;

import com.example.store.entity.model.AddressModel;
import com.example.store.entity.vo.AddressVO;
import com.example.store.entity.vo.DistrictVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {
	List<DistrictVO> getDistrict(String parent);

	Integer addAddress(AddressModel addressModel);

	String getDistrictNameByCode(String code);

	List<AddressVO> getAddresses(Integer uid);

	Integer clearDefault(Integer uid);

	Integer setDefault(Integer uid, Integer aid);

	Integer delete(Integer uid, Integer aid);

	AddressModel getAddressById(Integer id);
}
