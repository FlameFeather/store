package com.example.store.service;

import com.example.store.entity.dto.InfoChangeDTO;
import com.example.store.entity.dto.PasswordChangeDTO;
import com.example.store.entity.dto.UserLoginDTO;
import com.example.store.entity.vo.AvatarChangeVO;
import com.example.store.entity.vo.UserDetailsVO;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
	String login(UserLoginDTO userLoginDTO);

	String register(UserLoginDTO userLoginDTO);

	Boolean passwordChange(PasswordChangeDTO passwordChangeDTO);
	UserDetailsVO infoShow(String username);
	Integer infoChange(InfoChangeDTO infoChangeDTO);
	AvatarChangeVO avatarChange(String username, long maxSize,MultipartFile file);
}
