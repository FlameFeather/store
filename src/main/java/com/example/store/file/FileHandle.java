package com.example.store.file;

import com.example.store.exception.ServiceException;
import com.example.store.response.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class FileHandle {
	/**
	 * 用于格式化日期的工具对象
	 */
	private final DateTimeFormatter dateFormatter
			= DateTimeFormatter.ofPattern("yyyy/MM/dd");
	//  yyyy-MM-dd    c:/avatar/2022-07-16/...
	//  yyyy/MM/dd    c:/avatar/2022/7/16/...  推荐
	private final DateTimeFormatter dateTimeFormatter
			= DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	/**
	 * 根据日期生成文件夹名称
	 *
	 * @return 当前日期对应的文件夹名称
	 */
	private String generateDirNameByDate() {
		LocalDateTime now = LocalDateTime.now();
		return dateFormatter.format(now);
	}

	/**
	 * 根据时间戳生成 文件名称
	 *
	 * @return
	 */
	private String generateFileNameByTime() {
		LocalDateTime now = LocalDateTime.now();
		return dateTimeFormatter.format(now);
	}

	private final Random random = new Random();

	/**
	 * 生成 6位随机数 100000-999999
	 *
	 * @return
	 */
	private int getRandomValue() {
		return random.nextInt(900000) + 100000;
	}

	/**
	 * 上传文件的公用方法
	 *
	 * @return 服务器保存的文件路径
	 */
	public String uploadUserAvatar(MultipartFile file, long maxSize, List<String> contentTypes, String targetDirName, String username) {
		//判断是否存在上传数据
		if (file == null || file.isEmpty()) {
			throw new ServiceException(ResponseJson.State.ERR_BAO_REQUEST, "上传文件失败，请选择正确的文件再提交！");
		}
		//判断文件MIME类型 例如：image/png  image/jpeg...  -- 文件类型限制
		String contentType = file.getContentType();
		log.info("上传的文件类型：{}", contentType);
		if (!contentTypes.contains(contentType)) {
			throw new ServiceException(
					ResponseJson.State.ERR_NOT_ACCEPTABLE,
					"上传文件失败，不允许使用" + contentType + "类型的文件,允许的文件类型有" + contentTypes);
		}
		//判断文件大小 是否超出了限制
		if (file.getSize() > maxSize) {
			throw new ServiceException(
					ResponseJson.State.ERR_NOT_ACCEPTABLE,
					"上传文件失败，文件大小超出了限制(最大" + (maxSize / 1024) + "KB)!"
			);
		}
		try {
			//获取 tomcat本地的绝对路径+"/upload"
			File targetSubDir = new File(targetDirName + "\\" + username);
			if (!targetSubDir.exists()) {
				targetSubDir.mkdirs();//创建多层级文件夹
			}
			//上传的文件名 -- 上传的文件名称是做到唯一   uuid
//			String fileName = "" + generateFileNameByTime() + System.nanoTime() + getRandomValue();
			//上传的扩展名
			String originalFileName = file.getOriginalFilename();//请求参数中得到的文件名 index.png /
			int i = originalFileName.lastIndexOf(".");
			String suffix = originalFileName.substring(i); //".png"

			String targetFileName = username + suffix;
			//上传文件
			File dest = new File(targetSubDir, targetFileName);
			log.debug("dest:{}", dest);
			//从 dest绝对路径中 截取 服务器目录后面的子路径 保存在数据库中的是这个子路径
			String destRealPath = dest.getAbsolutePath();
			String destSubPath = destRealPath.substring(destRealPath.indexOf("\\image"));
			log.debug("destSubPath:{}", destSubPath);
			file.transferTo(dest);
			return destSubPath;
		} catch (Exception e) {
			throw new ServiceException(ResponseJson.State.ERR_INTERNAL_ERROR, "上传文件失败，请重新上传！");
		}
	}
}
