package com.example.expo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.expo.service.FileServiceI;

@RestController
public class FileHController {

	@Autowired
	private FileServiceI fsi;
	
	@GetMapping(value = "/upload")
	public String uploadFile(@RequestPart MultipartFile file,HttpServletRequest request) {
		String path = request.getContextPath();
		String str2 = request.getRemoteAddr();
		String str = request.getServerName();
//		ServletContext str1 = request.getServletContext();
		System.out.println(str2);
		System.out.println(str);
		System.out.println("Check file Data : " + file.getName());
		System.out.println("Check file Data : " + file.getOriginalFilename());
		fsi.uploadFile(file);
		return "File Uploaded.";
	}
}
