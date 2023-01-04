package com.example.store.service.impl;

import com.example.store.entity.dto.AvatarChangeDTO;
import com.example.store.entity.dto.InfoChangeDTO;
import com.example.store.entity.dto.PasswordChangeDTO;
import com.example.store.entity.dto.UserLoginDTO;
import com.example.store.entity.model.UserModel;
import com.example.store.entity.model.UserRoleModel;
import com.example.store.entity.vo.AvatarChangeVO;
import com.example.store.entity.vo.UserDetailsVO;
import com.example.store.exception.ServiceException;
import com.example.store.file.FileHandle;
import com.example.store.mapper.UserMapper;
import com.example.store.response.ResponseJson;
import com.example.store.security.JwtHandle;
import com.example.store.security.UserDetails;
import com.example.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
	private final UserDetailsService userDetailsService;
	private final UserMapper userMapper;
	private final JwtHandle jwtHandle;
	private final FileHandle fileHandle;

	@Autowired
	public UserServiceImpl(FileHandle fileHandle, UserDetailsService userDetailsService, JwtHandle jwtHandle, UserMapper userMapper) {
		this.userDetailsService = userDetailsService;
		this.jwtHandle = jwtHandle;
		this.userMapper = userMapper;
		this.fileHandle = fileHandle;
	}

	@Override
	public String login(UserLoginDTO userLoginDTO) {
		UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
		if (userDetails == null) {
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "登录失败，用户名不存在！");
		}
		if (!jwtHandle.matches(userLoginDTO.getPassword(), userDetails.getPassword())) {
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "登录失败，用户名或密码错误");
		}
		return jwtHandle.getJWT(userDetails);
	}

	@Override
	public String register(UserLoginDTO userLoginDTO) {
		if (userMapper.getByUsername(userLoginDTO.getUsername()) != null) {
			log.error("用户名重复！");
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "用户名重复！");
		}
		UserModel userModel = new UserModel();
		userModel.setUsername(userLoginDTO.getUsername());
		userModel.setPassword(jwtHandle.encoding(userLoginDTO.getPassword()));
		userModel.setCreatedUser(userLoginDTO.getUsername());
		userModel.setCreatedTime(LocalDateTime.now());
		userModel.setIsEnable(1);
		userModel.setModifiedUser(userLoginDTO.getUsername());
		userModel.setModifiedTime(LocalDateTime.now());
		userMapper.add(userModel);
		UserRoleModel userRoleModel = new UserRoleModel();
		userRoleModel.setUid(userModel.getUid());
		userRoleModel.setRid(1);
		userRoleModel.setCreatedUser(userModel.getUsername());
		userRoleModel.setCreatedTime(LocalDateTime.now());
		userRoleModel.setModifiedUser(userModel.getUsername());
		userRoleModel.setModifiedTime(LocalDateTime.now());
		userMapper.authorization(userRoleModel);
		UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
		return jwtHandle.getJWT(userDetails);
	}

	@Override
	public Boolean passwordChange(PasswordChangeDTO passwordChangeDTO) {
		UserModel userModel = userMapper.getByUsername(passwordChangeDTO.getUsername());
		if (!jwtHandle.matches(passwordChangeDTO.getOldPassword(), userModel.getPassword()))
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "原密码错误！");
		passwordChangeDTO.setModifiedTime(LocalDateTime.now());
		passwordChangeDTO.setNewPassword(jwtHandle.encoding(passwordChangeDTO.getNewPassword()));
		if (userMapper.updatePasswordById(passwordChangeDTO) != 1)
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "服务器错误！");
		return true;
	}

	@Override
	public UserDetailsVO infoShow(String username) {
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		UserModel userModel = userMapper.getByUsername(username);
		userDetailsVO.setUsername(userModel.getUsername());
		userDetailsVO.setEmail(userModel.getEmail());
		userDetailsVO.setGender(userModel.getGender());
		userDetailsVO.setPhone(userModel.getPhone());
		return userDetailsVO;
	}

	@Override
	public Integer infoChange(InfoChangeDTO infoChangeDTO) {
		return userMapper.infoChange(infoChangeDTO);
	}

	@Override
	public AvatarChangeVO avatarChange(String username, long maxSize, MultipartFile file) {
		String targetDirName = "D:\\JavaWebProgram\\store\\src\\main\\resources\\static\\image\\user\\avatar"; ///c:/brand-icon/huawei
		List<String> contentTypes = new ArrayList<>(Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/gif"));
		String fileName = fileHandle.uploadUserAvatar(file, maxSize, contentTypes, targetDirName, username);
		AvatarChangeDTO avatarChangeDTO = new AvatarChangeDTO();
		avatarChangeDTO.setAvatar(fileName);
		avatarChangeDTO.setModifiedTime(LocalDateTime.now());
		avatarChangeDTO.setModifiedUser(username);
		avatarChangeDTO.setUsername(username);
		userMapper.avatarChange(avatarChangeDTO);
		return new AvatarChangeVO(fileName, username);
	}

}
