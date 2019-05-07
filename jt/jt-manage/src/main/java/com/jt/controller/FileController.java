package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.FileVo;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;

	//要求:当实现了文件上传之后要求重定向到文件上传页面
	@RequestMapping("/file")
	public String fileImage(MultipartFile fileImage) throws IllegalStateException, IOException {
		//步骤1:获取文件名称
		String fileName = fileImage.getOriginalFilename();
		System.out.println("获取文件名称:"+fileName);
		//步骤2:指定文件目录 判断文件是否存在
		String filePath = "E:/CGB1812JT/jt-upload";
		File dirFile = new File(filePath);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//步骤3:实现文件上传
		String realName = "E:/CGB1812JT/jt-upload/"+fileName;
		fileImage.transferTo(new File(realName));
		return "redirect:/file.jsp";
		//forward 转发
	}
	
	//http://localhost:8091/pic/upload?dir=image
	//Content-Disposition: form-data; name="uploadFile"; filename="2446386_1_phelegthon_thumb.jpg"
	@RequestMapping("/pic/upload")
	@ResponseBody
	public FileVo uploadFile(MultipartFile uploadFile) {
		return fileService.upload(uploadFile);
	}
}
