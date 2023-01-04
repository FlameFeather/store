package com.example.store.mapper;

import com.example.store.entity.dto.AvatarChangeDTO;
import com.example.store.entity.dto.InfoChangeDTO;
import com.example.store.entity.dto.PasswordChangeDTO;
import com.example.store.entity.model.UserModel;
import com.example.store.entity.model.UserRoleModel;
import com.example.store.entity.vo.UserLoginVO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	UserLoginVO getLoginInfoByUsername(String username);

	Integer add(UserModel userModel);

	Integer authorization(UserRoleModel userRoleModel);

	Integer updatePasswordById(PasswordChangeDTO passwordChangeDTO);

	UserModel getById(Integer id);

	UserModel getByUsername(String username);

	Integer infoChange(InfoChangeDTO infoChangeDTO);

	Integer avatarChange(AvatarChangeDTO avatarChangeDTO);
}
