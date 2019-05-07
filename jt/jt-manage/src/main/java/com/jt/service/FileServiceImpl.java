package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.FileVo;

@Service //默认对象是单例,所以不能修改对象属性
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	//定义本地磁盘路径
	@Value("${image.dirPath}")
	private String localPath;
	@Value("${image.urlPath}")
	private String UrlPath;
	
	@Override
	public FileVo upload(MultipartFile uploadFile) {
		FileVo fileVo = new FileVo();
		//1.获取文件名称
		String fileName = uploadFile.getOriginalFilename();
		//2.将文件名统统小写,方便以后判断
		fileName = fileName.toLowerCase();
		//3.判断文件名的正确性
		//^开始,$结束,.除了回车换行之外的任意单个字符,+至少一个,*0到多个
		if(!fileName.matches("^.+\\.(png|jpg|gif)$")) {
			//表示文件类型不匹配
			fileVo.setError(1);
			return fileVo;
		}
		//4.判断是否是恶意程序
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			//4.1 获取宽度和高度
			int width = image.getWidth();
			int height = image.getHeight();
			//4.2 判断属性是否为0
			if(width==0 || height==0) {
				fileVo.setError(1);
				return fileVo; 
			}
			//5.根据时间生成文件夹
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//拼接文件夹的路径 E:/CGB1812JT/jt-upload/2019/04/08
			String localDir = localPath + dateDir;
			//创建文件
			File dirFile = new File(localDir);
			//判断文件是否存在,存在就不创建
			if(!dirFile.exists()) {
				dirFile.mkdirs();
			}
			//随机生成一个32位的16进制的数  1f422e4f-59ed-11e9-97b0-005056c00001并把-去掉
			String uuidName = UUID.randomUUID().toString().replace("-", "");
			//截取文件的后缀名和.
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			//把随机数和后缀名拼接
			String realName = uuidName+fileType;
			//创建新的文件夹
			File realFile = new File(localDir + "/" + realName);
			//上传文件
			uploadFile.transferTo(realFile);
			//封装fileVo对象
			String realUrlPath = UrlPath + dateDir + "/" + realName;
			fileVo.setWidth(width).setHeight(height).setUrl(realUrlPath);
		} catch (IOException e) {
			e.printStackTrace();
			fileVo.setError(1);
			return fileVo;
		}
		return fileVo;
	}

}
